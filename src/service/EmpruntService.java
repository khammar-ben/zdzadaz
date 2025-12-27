package service;

import model.Book;
import model.Emprunt;
import model.User;
import repository.EmpruntRepository;
import enums.EmpruntStatus;

import java.util.List;

public class EmpruntService {

    private EmpruntRepository empruntRepository;
    private final repository.BookRepository bookRepository = new repository.BookRepository();

    // Constructor
    public EmpruntService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    // User borrows a book (Book object must exist in DB)
    public Emprunt emprunterLivre(User user, Book book) {
        if (book == null || book.getId() == 0) {
            throw new RuntimeException("Book must exist in the database (provide a Book with a valid id)");
        }

        // Ensure the user exists in DB
        if (user == null || user.getId() == 0) {
            throw new RuntimeException("User must exist in the database (provide a User with a valid id)");
        }

        // Ensure book exists in DB
        Book persisted = bookRepository.findById(book.getId());
        if (persisted == null) {
            throw new RuntimeException("Book with id " + book.getId() + " not found in DB");
        }

        if (!persisted.isAvailable()) {
            throw new RuntimeException("Book not available");
        }

        // Decrease book quantity and persist
        persisted.decreaseQuantity();
        bookRepository.update(persisted);

        // Create new Emprunt using DB-generated ID
        Emprunt emprunt = new Emprunt(0, user, persisted);

        // Save to repository (will set generated ID)
        empruntRepository.save(emprunt);

        return emprunt;
    }

    // User borrows a book by its DB id
    public Emprunt emprunterLivreByBookId(User user, int bookId) {
        Book persisted = bookRepository.findById(bookId);
        if (persisted == null) {
            throw new RuntimeException("Book with id " + bookId + " not found in DB");
        }

        return emprunterLivre(user, persisted);
    }

    // User returns a book
    public void retournerLivre(Emprunt emprunt) {
        if (emprunt.getStatus() != EmpruntStatus.EN_COURS) {
            throw new RuntimeException("Emprunt already closed");
        }

        // Close the emprunt and increase book quantity, persist both
        emprunt.closeEmprunt();
        empruntRepository.update(emprunt);
        if (emprunt.getBook() != null) {
            Book persisted = bookRepository.findById(emprunt.getBook().getId());
            if (persisted != null) {
                persisted.increaseQuantity();
                bookRepository.update(persisted);
            } else {
                // As fallback, update the book object on the emprunt
                emprunt.getBook().increaseQuantity();
                bookRepository.update(emprunt.getBook());
            }
        }
    }

    // Check if an emprunt is late
    public boolean estEnRetard(Emprunt emprunt) {
        return emprunt.isLate() && emprunt.getStatus() == EmpruntStatus.EN_COURS;
    }

    // List all emprunts
    public List<Emprunt> getAllEmprunts() {
        return empruntRepository.findAll();
    }

    // Get emprunt by ID
    public Emprunt getEmpruntById(int id) {
        Emprunt e = empruntRepository.findById(id);
        if (e == null) {
            throw new RuntimeException("Emprunt not found");
        }
        return e;
    }

    // Update an emprunt (e.g., change status, return date)
    public Emprunt updateEmprunt(Emprunt emprunt) {
        boolean updated = empruntRepository.update(emprunt);
        if (!updated) {
            throw new RuntimeException("Emprunt not found for update");
        }
        return emprunt;
    }

    // Delete emprunt by ID
    public boolean deleteEmpruntById(int id) {
        return empruntRepository.deleteById(id);
    }
}
