import model.Book;
import model.Client;
import model.Emprunt;
import repository.BookRepository;
import repository.EmpruntRepository;
import service.BookService;
import service.EmpruntService;

public class Main {

    public static void main(String[] args) {

/*
        BookService service = new BookService(new BookRepository());

        // CREATE
        Book book = new Book("Java OOP1", "Oracle Press1", "Learn OOP1", 10, 1);
        service.addBook(book);


        // READ ALL
        service.getAllBooks().forEach(b ->
                System.out.println(b.getTitle())
        );


        // UPDATE
        Book existing = service.getBookById(1);
        if (existing != null) {
            existing.setQuantity(50);
            service.updateBook(existing);
        }


        // DELETE
        service.deleteBook(6L);
*/
        // ===== Emprunt (borrow) demo =====
        repository.UserRepository userRepo = new repository.UserRepository();
        EmpruntService empruntService = new EmpruntService(new EmpruntRepository());
        BookService bookService = new BookService(new BookRepository());

        // Ensure a user exists (create if not)
        String userEmail = "newuser@libb.com";
        model.User user = userRepo.findByEmail(userEmail);
        if (user == null) {
            user = new model.Client(userEmail, "1234");
            userRepo.save(user);
            System.out.println("Created user id: " + user.getId());
        }

        // Example: borrow an existing DB book by id
        int bookIdToBorrow = 25; // <- change to a real book id in your DB
        Book dbBook = bookService.getBookById(bookIdToBorrow);
        if (dbBook == null) {
            System.out.println("Book id " + bookIdToBorrow + " not found in DB. Create a book first or choose an existing id.");
        } else {
            Emprunt emprunt = empruntService.emprunterLivreByBookId(user, dbBook.getId());
            System.out.println("Emprunt created id: " + emprunt.getId());
        }
/*
        // list all emprunts
        empruntService.getAllEmprunts().forEach(e ->
                System.out.println("Emprunt id: " + e.getId() + " status: " + e.getStatus())
        );

        // get by id and update (no-op example)
        Emprunt fetched = empruntService.getEmpruntById(emprunt.getId());
        fetched.setStatus(EmpruntStatus.EN_COURS);
        empruntService.updateEmprunt(fetched);

        // return book
        empruntService.retournerLivre(fetched);
        System.out.println("After return status: " + fetched.getStatus());

        // delete emprunt
        empruntService.deleteEmpruntById(fetched.getId());
*/
    }
}
