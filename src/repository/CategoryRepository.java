package repository;

import model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private List<Category> categories = new ArrayList<>();

    public void save(Category category) {
        categories.add(category);
    }

    public List<Category> findAll() {
        return categories;
    }
}
