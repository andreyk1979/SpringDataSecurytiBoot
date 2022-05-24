package com.kuimov.pp.task312.controller;

import com.kuimov.pp.task312.service.RoleService;
import com.kuimov.pp.task312.service.UserService;
import com.kuimov.pp.task312.models.Role;
import com.kuimov.pp.task312.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String viewWelcomePage() {
        return "welcome_page";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processUserRegistration(@ModelAttribute User user,
                                  @RequestParam(value = "roless",
                                          required = false,
                                          defaultValue = "ROLE_USER") Set<String> roles) {
        Set<Role> setRoles = roleService.getSetRoles(roles);
        user.setRoles(setRoles);
        userService.save(user);
        return "register_success";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "usersForm";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        Set<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_editForm";
    }

    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        userService.delete(user);
        return "redirect:/admin/users";
    }
}
