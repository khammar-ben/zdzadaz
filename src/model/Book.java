package model;

public class Book {

    private int id;
    private String title;
    private String author;
    private String description;
    private int quantity;
    private int category;



    public Book(int id, String title, String author, String description, int quantity, int category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public Book(String title, String author, String description, int quantity, int category) {
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

    // ===== Getters =====
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCategory() {
        return category;
    }

    // ===== Setters =====
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(Category category) {
        this.category = category.getId();
    }

}
