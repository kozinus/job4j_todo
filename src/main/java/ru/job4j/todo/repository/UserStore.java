package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.*;

@Repository
@AllArgsConstructor
public class UserStore {

    private final static Logger LOG = LoggerFactory.getLogger(UserStore.class.getName());

    private final CrudStore crudStore;

    private static final String DEFTZ = TimeZone.getDefault().getID();

    public Optional<User> save(User user) {
        try {
            if (user.getTimezone() == null) {
                user.setTimezone(DEFTZ);
            }
            crudStore.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error(e.getMessage(), user);
        }
        return Optional.empty();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return crudStore.optional("from User where email = :fEmail and password = :fPass", User.class,
                Map.of("fEmail", email, "fPass", password));
    }

    public Collection<User> findAll() {
        return crudStore.query("from User order by id asc", User.class);
    }

    public void deleteById(int id) {
        crudStore.run(
                "delete from User where id = :fId",
                Map.of("fId", id)
        );
    }
}
