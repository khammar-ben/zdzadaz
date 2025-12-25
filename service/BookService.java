package service;

import model.Book;
import repository.BookRepository;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Admin adds a book
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    // Anyone can list books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
