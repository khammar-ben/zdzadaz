package model;

public abstract class User {

    protected Long id;
    protected String email;
    protected String password;

    public User(String email, String password) {
        this.id = System.currentTimeMillis();
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
