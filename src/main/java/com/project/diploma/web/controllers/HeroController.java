package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Gender;
import com.project.diploma.data.models.Hero;
import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.services.AuthenticatedUserService;
import com.project.diploma.service.services.HeroService;
import com.project.diploma.web.models.CreateHeroModel;
import com.project.diploma.web.models.LoginUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;
    private final ModelMapper mapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public HeroController(HeroService heroService, ModelMapper mapper, AuthenticatedUserService authenticatedUserService) {
        this.heroService = heroService;
        this.mapper = mapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @ModelAttribute("hero")
    public CreateHeroModel model(){
        return new CreateHeroModel();
    }

    @GetMapping("/create")
    public String viewHeroPage(@ModelAttribute("hero") CreateHeroModel model){
        return "heroes/create";
    }

    @PostMapping("/create")
    public String createHero(@Valid @ModelAttribute("hero") CreateHeroModel model, BindingResult result,
                             Principal principal, HttpSession session) throws Exception {
        if (result.hasErrors()){
            return "heroes/create";
        }

        String username = principal.getName();

        CreateHeroServiceModel hero = mapper.map(model, CreateHeroServiceModel.class);
        hero.setGender(Gender.valueOf(model.getGender().toUpperCase()));
        heroService.createHero(hero, username);
        return "redirect:/home";
    }

    @GetMapping("/select")
    public String selectHero(HttpSession session){
        String username = authenticatedUserService.getUsername();
        long count = heroService.getCountOfHeroes(username);
        session.setAttribute("countHeroes", count);
        if(count != 0){
            List<Hero> heroes = heroService.getAllUserHeroes(username);
            session.setAttribute("heroes", heroes);
        }
        return "heroes/select";
    }

    @GetMapping("/opponent/{name}")
    public String selectOpponent(@PathVariable String name, ModelAndView model, HttpSession session){
        String username = authenticatedUserService.getUsername();
//todo 
        return null;
    }

//    @GetMapping("/create-hero")
//    public String heroName(HttpSession session){
//        String username = authenticatedUserService.getUsername();
//        DetailsHeroServiceModel hero = heroService.getByUsername(username);
//        session.setAttribute("name", hero.getName());
//        return "hero/create-hero";
//    }

//    @GetMapping("/details/{name}")
//    public ModelAndView details(@PathVariable String name, ModelAndView model){
//        DetailsHeroServiceModel details = heroService.findHero(name);
//        model.addObject("details", details);
//        model.setViewName("/hero/hero-details");
//        return model;
//    }

//    @GetMapping("/fight/{name}")
//    public ModelAndView fight(@PathVariable String name, ModelAndView model, HttpSession session){
//        LoginUserModel user = (LoginUserModel) session.getAttribute("user");
//        String result = heroService.fight(user.getHeroName(), name);
//        HeroModel home = heroService.findNameAndGenderHero(user.getHeroName());
//        HeroModel guest = heroService.findNameAndGenderHero(name);
//        model.addObject("home", home);
//        model.addObject("guest", guest);
//        model.addObject("result", result);
//        model.setViewName("/hero/fight");
//        return model;
//    }
}
