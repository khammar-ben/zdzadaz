package model;

import java.time.LocalDate;
import enums.EmpruntStatus;

public class Emprunt {

    private Long id;
    private Client client;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private EmpruntStatus status;

    public Emprunt(Long id, Client client, Book book) {
        this.id = id;
        this.client = client;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.status = EmpruntStatus.EN_COURS;
    }

    public void closeEmprunt() {
        this.returnDate = LocalDate.now();
        this.status = EmpruntStatus.RETOURNE;
        book.increaseQuantity();
    }

    public boolean isLate() {
        return borrowDate.plusDays(14).isBefore(LocalDate.now());
    }

    public EmpruntStatus getStatus() {
        return status;
    }

    public Book getBook() {
        return book;
    }
}
