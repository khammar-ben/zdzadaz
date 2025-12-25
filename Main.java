import model.Book;
import model.Client;
import model.Category;
import model.Emprunt;
import repository.BookRepository;
import repository.EmpruntRepository;
import service.EmpruntService;
import enums.EmpruntStatus;

public class Main {
    public static void main(String[] args) {

        // Repositories
        BookRepository bookRepo = new BookRepository();
        EmpruntRepository empruntRepo = new EmpruntRepository();

        // Service
        EmpruntService empruntService = new EmpruntService(empruntRepo);

        // Sample category and books
        Category cat = new Category(1L, "Programming");
        Book book1 = new Book(1L, "Java Basics", "John Doe", "Learn Java", 2, cat);
        Book book2 = new Book(2L, "OOP in Java", "Jane Doe", "Advanced OOP", 1, cat);

        bookRepo.save(book1);
        bookRepo.save(book2);

        // Sample client
        Client client = new Client("test@mail.com", "password123");

        // Borrow books
        Emprunt emprunt1 = empruntService.emprunterLivre(client, book1);
        Emprunt emprunt2 = empruntService.emprunterLivre(client, book2);

        // Return one book
        empruntService.retournerLivre(emprunt1);

        // Print emprunts
        for (Emprunt e : empruntService.getAllEmprunts()) {
            System.out.println(
                    e.getBook().getTitle() + " | Status: " + e.getStatus()
            );
        }
    }
}
