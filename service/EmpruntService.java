package service;

import model.Book;
import model.Client;
import model.Emprunt;
import repository.EmpruntRepository;
import enums.EmpruntStatus;

import java.util.List;

public class EmpruntService {

    private EmpruntRepository empruntRepository;
    private long empruntIdCounter = 1; // simple ID generator

    // Constructor
    public EmpruntService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    // Client borrows a book
    public Emprunt emprunterLivre(Client client, Book book) {
        if (!book.isAvailable()) {
            throw new RuntimeException("Book not available");
        }

        // Decrease book quantity
        book.decreaseQuantity();

        // Create new Emprunt using correct constructor
        Emprunt emprunt = new Emprunt(empruntIdCounter++, client, book);

        // Save to repository
        empruntRepository.save(emprunt);

        return emprunt;
    }

    // Client returns a book
    public void retournerLivre(Emprunt emprunt) {
        if (emprunt.getStatus() != EmpruntStatus.EN_COURS) {
            throw new RuntimeException("Emprunt already closed");
        }

        // Close the emprunt and increase book quantity
        emprunt.closeEmprunt();
    }

    // Check if an emprunt is late
    public boolean estEnRetard(Emprunt emprunt) {
        return emprunt.isLate() && emprunt.getStatus() == EmpruntStatus.EN_COURS;
    }

    // List all emprunts
    public List<Emprunt> getAllEmprunts() {
        return empruntRepository.findAll();
    }
}
