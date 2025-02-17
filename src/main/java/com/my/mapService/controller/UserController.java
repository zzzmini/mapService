package com.my.mapService.controller;

import com.my.mapService.dto.User;
import com.my.mapService.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "/users/login";
    }

    @GetMapping("signup")
    public String signIn() {
        return "/users/signup";
    }

    @PostMapping("signup")
    public String signup(User user) {
        System.out.println(user);
        userService.signUp(user);
        return "redirect:/userList";
    }

    @GetMapping("userList")
    public String userList(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("userList", users);
        return "/users/userList";
    }

    @GetMapping("/user/update/{id}")
    public String update(@PathVariable("id") String id
                    , Model model) {
        Optional<User> findUser = userService.findById(id);
        model.addAttribute("user", findUser);
        return "/users/updateUser";
    }

    @PostMapping("/user/update")
    public String updateUser(User user) {
        userService.update(user);
        return "redirect:/userList";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        userService.deleteById(id);
        return "redirect:/userList";
    }
}
