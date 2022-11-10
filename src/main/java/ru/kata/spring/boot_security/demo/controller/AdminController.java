package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImp userServiceImp, RoleService roleService) {
        this.userServiceImp = userServiceImp;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPart() {
        return "admin";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("userList", userServiceImp.getUsers());
        return "users";
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {

        User user = new User();
        model.addAttribute("listRoles",roleService.getAllRoles());
        model.addAttribute("user", user);
        return "newUser";
    }

    @PostMapping("/users")
    public String pageCreate(@ModelAttribute("user")
                             @Valid User user, BindingResult bindingResult,
                             @RequestParam("listRoles") ArrayList<Integer> roles) {
        user.setRoles(roleService.findByIdRoles(roles));
        userServiceImp.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String pageEditUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user",userServiceImp.getUser(id));
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "edit";
    }

    @PutMapping("/edit")
    public String pageEdit(@Valid User user, BindingResult bindingResult,
                           @RequestParam("listRoles") ArrayList<Integer> roles) {
        user.setRoles(roleService.findByIdRoles(roles));
        userServiceImp.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete")
    public String delete(@RequestParam("userId") int id) {
        userServiceImp.delete(id);

        return "redirect:/admin/users";
    }
}