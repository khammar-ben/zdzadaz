package service;

import model.*;
import repository.UserRepository;

public class AuthService {
    private UserRepository repo = new UserRepository();

    public void registerClient(Client c) throws Exception {
        repo.save(c, "CLIENT");
    }

    public User login(String email, String password) throws Exception {
        return repo.findByEmailAndPassword(email, password);
    }
}
