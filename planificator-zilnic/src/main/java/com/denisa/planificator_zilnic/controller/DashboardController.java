package com.denisa.planificator_zilnic.controller;

import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.service.TaskService;
import com.denisa.planificator_zilnic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;
    private final TaskService taskService;

    public DashboardController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return "redirect:/admin/users";
        }

        List<Task> tasks = taskService.getTasksForUser(user);

        long total = tasks.size();
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = total - completed;

        model.addAttribute("username", user.getUsername());
        model.addAttribute("total", total);
        model.addAttribute("completed", completed);
        model.addAttribute("pending", pending);

        return "dashboard";
    }
}
