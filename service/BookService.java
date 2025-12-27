package service;

import repository.BookRepository;
import model.Book;
import java.util.List;

public class BookService {
    private BookRepository repo = new BookRepository();

    public void add(Book b) throws Exception {
        repo.save(b);
    }

    public List<Book> list() throws Exception {
        return repo.findAll();
    }
}
