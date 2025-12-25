package model;

public class Book {

    private Long id;
    private String title;
    private String author;
    private String description;
    private int quantity;
    private Category category;

    public Book(Long id, String title, String author, String description, int quantity, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void increaseQuantity() {
        quantity++;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }
}
