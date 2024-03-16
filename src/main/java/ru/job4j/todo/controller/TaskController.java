package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final PriorityService priorityService;

    private final CategoryService categoryService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll().stream().distinct().collect(Collectors.toList()));
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findAllDone());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findAllNew());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam List<Integer> categoriesId, Model model) {
        task.setUser(user);
        task.setParticipates(categoryService.findCategoriesByIds(categoriesId).stream().toList());
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/makedone/{id}")
    public String makeDone(Model model, @PathVariable int id) {
        if (!taskService.makeDone(id)) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return getAll(model);
    }

    @GetMapping("/edit/{id}")
    public String getByIdToEdit(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        Task task = taskOptional.get();
        model.addAttribute("task", task);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, @RequestParam List<Integer> categoriesId, Model model) {
        task.setParticipates(categoryService.findCategoriesByIds(categoriesId).stream().toList());
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задача с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/tasks";
    }
}
