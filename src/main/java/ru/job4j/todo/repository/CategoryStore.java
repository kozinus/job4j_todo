package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.User;

import java.util.*;

@Repository
@AllArgsConstructor
public class CategoryStore {
    private final static Logger LOG = LoggerFactory.getLogger(UserStore.class.getName());

    private final CrudStore crudStore;

    public Optional<Category> save(Category category) {
        try {
            crudStore.run(session -> session.persist(category));
            return Optional.of(category);
        } catch (Exception e) {
            LOG.error(e.getMessage(), category);
        }
        return Optional.empty();
    }

    public Optional<Category> findById(int id) {
        return crudStore.optional("from Category where id = :fId", Category.class,
                Map.of("fId", id));
    }

    public Collection<Category> findAll() {
        return crudStore.query("from Category order by id asc", Category.class);
    }

    public Collection<Category> findAllByIds(List<Integer> ids) {
        return crudStore.query("from Category where id in :fIds", Category.class,
                Map.of("fIds", ids));
    }

    public void deleteById(int id) {
        crudStore.run(
                "delete from Category where id = :fId",
                Map.of("fId", id)
        );
    }
}
