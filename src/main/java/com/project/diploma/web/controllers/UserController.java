package com.project.diploma.web.controllers;

import com.project.diploma.services.models.RegisterUserServiceModel;
import com.project.diploma.services.services.UserService;
import com.project.diploma.web.models.LoginUserModel;
import com.project.diploma.web.models.ProfileUserModel;
import com.project.diploma.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @ModelAttribute("register")
    public RegisterUserModel reg() {
        return new RegisterUserModel();
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("register") RegisterUserModel model) {
        return "users/register";
    }

    @PostMapping("/register")
    public String confirmRegister(@Valid @ModelAttribute("register") RegisterUserModel model, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (result.getAllErrors().stream()
                    .filter(error -> error.getCode().equals("EqualsPasswordValidation")).count() > 0) {
                session.setAttribute("registerError", "Passwords not equals.");
            }
            return "users/register";
        }

        RegisterUserServiceModel user = this.mapper.map(model, RegisterUserServiceModel.class);
        if (userService.register(user)) {
            return "redirect:/users/login";
        } else {
            return "redirect:/users/register";
        }
    }

    @ModelAttribute("login")
    public LoginUserModel log() {
        return new LoginUserModel();
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginConfirm(@ModelAttribute("login") LoginUserModel model, @RequestParam String error, HttpSession session) {

        session.setAttribute("user", model.getUsername().equals("") ? "Username can not be empty." : "");

        session.setAttribute("pass", model.getPassword().equals("") ? "Password can not be empty." : "");

        session.setAttribute("error", !model.getPassword().equals("") &&
                !model.getUsername().equals("") ? "Incorrect username or password." : "");

        return "redirect:/users/login";
    }

    @GetMapping("profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        ProfileUserModel user = userService.getDetailsForUser(username);
        modelAndView.addObject("profile", user);
        modelAndView.setViewName("/users/profile");
        return modelAndView;
    }
}
