/*import model.*;
import repository.*;
import service.*;

public class Main {

    public static void main(String[] args) throws Exception {

        CategoryRepository catRepo = new CategoryRepository();
        BookService bookService = new BookService();
        EmpruntService empruntService = new EmpruntService();

        Category cat = new Category(1L, "Programming");
        catRepo.save(cat);

        Book book = new Book(
                1L, "Java JDBC", "Oracle", "Exam project", 2, cat
        );
        bookService.addBook(book);

        Client client = new Client("test@mail.com", "123");
        client.setId(1L);

        Emprunt e = empruntService.borrow(1L, client, book);
        empruntService.returnBook(e);

        System.out.println("FULL DB PROJECT WORKS");
    }
}

*/
