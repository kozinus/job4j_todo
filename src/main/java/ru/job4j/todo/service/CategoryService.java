package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryStore;
import ru.job4j.todo.repository.UserStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryStore categoryStore;

    public Optional<Category> save(Category category) {
        return categoryStore.save(category);
    }

    public Optional<Category> findCategoryById(int id) {
        return categoryStore.findById(id);
    }

    public Collection<Category> findAll() {
        return categoryStore.findAll();
    }

    public Collection<Category> findCategoriesByIds(List<Integer> ids) {
        return categoryStore.findAllByIds(ids);
    }
}
