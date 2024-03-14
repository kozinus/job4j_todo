package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final CrudStore crudStore;

    public Task save(Task task) {
        crudStore.run(session -> session.persist(task));
        return task;
    }

    public boolean update(Task task) {
        boolean result;
        try {
            crudStore.run(session -> session.merge(task));
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public void delete(int id) {
        crudStore.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    public boolean makeDone(int id) {
        boolean result;
        try {
            crudStore.run(
                    "update Task set done = true where id = :fId",
                    Map.of("fId", id)
            );
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public List<Task> findAll() {
        return crudStore.query("from Task t JOIN FETCH t.priority order by t.id asc", Task.class);
    }

    public List<Task> findAllDone() {
        return crudStore.query("from Task t JOIN FETCH t.priority where t.done = true order by t.id asc", Task.class);
    }

    public List<Task> findAllNew() {
        return crudStore.query("from Task t JOIN FETCH t.priority where t.created > :now order by t.id asc", Task.class,
                Map.of("now", LocalDateTime.now().withNano(0).withSecond(0).minusMinutes(3)));
    }

    public Optional<Task> findById(int id) {
        return crudStore.optional("from Task t JOIN FETCH t.priority where t.id = :Id", Task.class,
                Map.of("Id", id));
    }
}
