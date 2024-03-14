package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriorityService {
    private final PriorityStore priorityStore;

    public Optional<Priority> findById(int id) {
        return priorityStore.findById(id);
    }

    public Collection<Priority> findAll() {
        return priorityStore.findAll();
    }
}
