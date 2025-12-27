package model;

import java.time.LocalDate;
import enums.EmpruntStatus;

public class Emprunt {

    private int id;
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private EmpruntStatus status;

    public Emprunt(int id, User user, Book book) {
        this.id = id;
        this.user = user;
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

    // ===== Getters & Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public EmpruntStatus getStatus() {
        return status;
    }

    public void setStatus(EmpruntStatus status) {
        this.status = status;
    }
}
