package repository;

import model.Emprunt;
import java.util.ArrayList;
import java.util.List;

public class EmpruntRepository {

    private List<Emprunt> emprunts = new ArrayList<>();

    public void save(Emprunt emprunt) {
        emprunts.add(emprunt);
    }

    public List<Emprunt> findAll() {
        return emprunts;
    }
}
