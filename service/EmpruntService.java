package service;

import repository.EmpruntRepository;

public class EmpruntService {
    private EmpruntRepository repo = new EmpruntRepository();

    public void borrow(Long userId, Long bookId) throws Exception {
        repo.borrow(userId, bookId);
    }
}
