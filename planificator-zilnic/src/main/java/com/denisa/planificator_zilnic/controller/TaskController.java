package com.denisa.planificator_zilnic.controller;

import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.service.TaskService;
import com.denisa.planificator_zilnic.service.UserService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public String listTasks(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Task> tasks = taskService.getTasksForUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("task") Task task,
                          BindingResult result,
                          Authentication authentication) {

        if (result.hasErrors()) {
            return "add-task";
        }

        User user = userService.findByUsername(authentication.getName());
        task.setUser(user);

        if (task.getDueDate() == null) {
            task.setDueDate(LocalDate.now());
        }

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        Task task = taskService.getTaskById(id);
        User user = userService.findByUsername(authentication.getName());

        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id,
                           @Valid @ModelAttribute("task") Task updatedTask,
                           BindingResult result,
                           Authentication authentication,
                           Model model) {

        if (result.hasErrors()) {
            model.addAttribute("task", updatedTask);
            return "edit-task";
        }

        Task existing = taskService.getTaskById(id);
        User user = userService.findByUsername(authentication.getName());

        if (existing == null || !existing.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setDueDate(updatedTask.getDueDate());
        existing.setCompleted(updatedTask.isCompleted());

        taskService.saveTask(existing);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, Authentication authentication) {
        Task task = taskService.getTaskById(id);
        User user = userService.findByUsername(authentication.getName());

        if (task != null && task.getUser().getId().equals(user.getId())) {
            taskService.deleteTask(id);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/stats")
    public String taskStats(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Task> tasks = taskService.getTasksForUser(user);

        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = total - completed;

        model.addAttribute("total", total);
        model.addAttribute("completed", completed);
        model.addAttribute("pending", pending);

        return "task-stats";
    }

    @GetMapping("/pdf")
    public void exportTasksToPdf(HttpServletResponse response, Authentication authentication) throws Exception {
        User user = userService.findByUsername(authentication.getName());
        List<Task> tasks = taskService.getTasksForUser(user);

        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = total - completed;

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tasks.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("STATISTICI TASK-URI"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total task-uri: " + total));
        document.add(new Paragraph("Finalizate: " + completed));
        document.add(new Paragraph("Nefinalizate: " + pending));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("LISTA TASK-URI:"));
        document.add(new Paragraph(" "));

        for (Task t : tasks) {
            document.add(new Paragraph("- " + t.getTitle()));
        }

        document.close();
    }
}
