package com.denisa.planificator_zilnic.controller;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.service.RoleService;
import com.denisa.planificator_zilnic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @GetMapping("/edit-role/{id}")
    public String editUserRole(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin-edit-role";
    }

    @PostMapping("/edit-role/{id}")
    public String updateUserRole(@PathVariable Long id, @RequestParam Long roleId) {
        userService.updateUserRole(id, roleId);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
