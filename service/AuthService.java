package service;

import model.Admin;
import model.Client;
import model.Responsable;
import model.User;
import repository.UserRepository;

public class AuthService {

    private UserRepository userRepository;
    private User currentUser;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER CLIENT
    public void registerClient(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already used");
        }
        userRepository.save(new Client(email, password));
    }

    // REGISTER ADMIN
    public void registerAdmin(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already used");
        }
        userRepository.save(new Admin(email, password));
    }

    // REGISTER RESPONSABLE
    public void registerResponsable(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already used");
        }
        userRepository.save(new Responsable(email, password));
    }

    // LOGIN
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        this.currentUser = user;
    }

    // LOGOUT
    public void logout() {
        currentUser = null;
    }

    // SESSION
    public User getCurrentUser() {
        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }
        return currentUser;
    }

    // TYPE CHECKS
    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    public boolean isClient() {
        return currentUser instanceof Client;
    }

    public boolean isResponsable() {
        return currentUser instanceof Responsable;
    }
}
