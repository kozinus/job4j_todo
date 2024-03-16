package ru.job4j.todo.model;

import lombok.*;
import org.hibernate.engine.profile.Fetch;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private LocalDateTime created = LocalDateTime.now().withSecond(0).withNano(0);

    private boolean done = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> participates = new ArrayList<>();
}
