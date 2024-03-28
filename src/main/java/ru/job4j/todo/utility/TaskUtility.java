package ru.job4j.todo.utility;

import ru.job4j.todo.model.Task;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

public class TaskUtility {
    public static List<Task> taskTimeZoneShift(Collection<Task> tasks, String timezone) {
        return tasks.stream().peek(x -> x.setCreated(x.getCreated().atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(timezone)).toLocalDateTime())).toList();
    }
}
