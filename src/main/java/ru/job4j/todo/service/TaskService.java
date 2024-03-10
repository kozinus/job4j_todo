package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskStore;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskStore taskStore;

    public Task save(Task task) {
        return taskStore.save(task);
    }

    public boolean deleteById(int id) {
        var fileOptional = findById(id);
        boolean isPresent = fileOptional.isPresent();
        if (isPresent) {
            taskStore.delete(id);
        }
        return isPresent;
    }

    public boolean update(Task task) {
        return taskStore.update(task);
    }

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    public Collection<Task> findAllDone() {
        return taskStore.findAllDone();
    }

    public Collection<Task> findAllNew() {
        return taskStore.findAllNew();
    }
}
