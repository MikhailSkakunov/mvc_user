package ru.gb.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.dao.UserDAO;
import ru.gb.models.User;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;

    @Autowired
    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("allUsers", model.addAttribute(userDAO.findAll()));
        return "user/users";
    }

   @GetMapping("/{id}")
    public String findById(@PathVariable("id") int id, Model model) {
       model.addAttribute("oneUser", model.addAttribute(userDAO.findById(id)));
        return "user/id";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userDAO.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") int id, Model model) {
        userDAO.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", userDAO.findById(id));
        return "user/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "user/update";

        userDAO.update(id, user);
        return "redirect:/users";
    }
}
