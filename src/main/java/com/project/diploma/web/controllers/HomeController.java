package com.project.diploma.web.controllers;

import com.project.diploma.services.services.UserService;
import com.project.diploma.web.models.HeroPictureModel;
import com.project.diploma.web.models.LoggedUserFilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "home/index";
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session){
        LoggedUserFilterModel model = (LoggedUserFilterModel) session.getAttribute("authorities");
        int countRoles = model.getAuthorities().size();
        if (countRoles == 1){
            String username = (String) session.getAttribute("username");
            int gold = userService.takeGoldFromUser(username);
            session.setAttribute("gold", gold);
        }

        HeroPictureModel hero = userService.getHero(model.getUsername());
        modelAndView.addObject("randomHero", hero);

        modelAndView.setViewName("/home/home");
        return modelAndView;
    }
}
