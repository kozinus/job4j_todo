package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserStore;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserStore userStore;

    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userStore.findByEmailAndPassword(email, password);
    }
}
