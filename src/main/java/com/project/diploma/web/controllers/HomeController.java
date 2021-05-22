package com.project.diploma.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession session){
        return "home/index";
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
        modelAndView.setViewName("/home/home");
        return modelAndView;
    }
}
