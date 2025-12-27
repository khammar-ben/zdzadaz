package model;

import enums.EmpruntStatus;

public class Emprunt {
    private Long id;
    private Long userId;
    private Long bookId;
    private EmpruntStatus status;

    public Emprunt(Long id, Long userId, Long bookId, EmpruntStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getBookId() { return bookId; }
    public EmpruntStatus getStatus() { return status; }
}
