package service;

import model.Book;
import repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) {
        repository.save(book);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(int id) {
        return repository.findById(id);
    }

    public void updateBook(Book book) {
        repository.update(book);
    }

    public void deleteBook(int id) {
        repository.delete(id);
    }

}
