package com.project.diploma.web.controllers;

import com.project.diploma.data.repository.UserRepository;
import com.project.diploma.service.services.HeroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final HeroService heroService;
    private final ModelMapper mapper;
    private final UserRepository userRepository;


    @Autowired
    public HomeController(HeroService heroService, ModelMapper mapper, UserRepository userRepository) {
        this.heroService = heroService;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(HttpSession session){
        return "home/index";
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
//        String username = principal.getName();
//        List<HomeViewModel> heroes = heroService.getAllWithoutCurrent(username)
//                .stream()
//                .map(m -> mapper.map(m, HomeViewModel.class))
//                .collect(Collectors.toList());
//        modelAndView.addObject("heroes", heroes);
        modelAndView.setViewName("/home/home");
        return modelAndView;
    }
}
