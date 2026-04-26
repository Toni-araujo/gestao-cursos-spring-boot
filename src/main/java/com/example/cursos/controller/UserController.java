package com.example.cursos.controller;

import com.example.cursos.model.User;
import com.example.cursos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/login/add")
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_user";
        }

        if (userService.findUserWithRoleByName(user.getName()).isPresent()) {
            model.addAttribute("error", "Usuário '" + user.getName() + "' já existe!");
            model.addAttribute("user", new User());
            return "add_user";
        }

        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}