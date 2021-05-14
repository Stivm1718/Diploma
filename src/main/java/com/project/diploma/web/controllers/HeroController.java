package com.project.diploma.web.controllers;

import com.project.diploma.service.models.CreateHeroServiceModel;
import com.project.diploma.service.models.DetailsHeroServiceModel;
import com.project.diploma.service.models.LoginServiceModel;
import com.project.diploma.service.services.AuthenticatedUserService;
import com.project.diploma.service.services.HeroService;
import com.project.diploma.web.models.HeroModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;
    private final ModelMapper mapper;
    private final AuthenticatedUserService authenticatedUserService;

    public HeroController(HeroService heroService, ModelMapper mapper, AuthenticatedUserService authenticatedUserService) {
        this.heroService = heroService;
        this.mapper = mapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    @ModelAttribute("hero")
    public CreateHeroServiceModel model(){
        return new CreateHeroServiceModel();
    }

    @GetMapping("/create")
    public String viewHeroPage(@ModelAttribute("hero") CreateHeroServiceModel model){
        return "/hero/create-hero";
    }

    @PostMapping("/create")
    public String createHero(@Valid @ModelAttribute("hero") CreateHeroServiceModel model, BindingResult result,
                             Principal principal) throws Exception {
        if (result.hasErrors()){
            return "hero/create-hero";
        }

        String username = principal.getName();

        CreateHeroServiceModel hero = mapper.map(model, CreateHeroServiceModel.class);

        heroService.createHero(hero, username);
        return "redirect:/home";
    }

//    @GetMapping("/create-hero")
//    public String heroName(HttpSession session){
//        String username = authenticatedUserService.getUsername();
//        DetailsHeroServiceModel hero = heroService.getByUsername(username);
//        session.setAttribute("name", hero.getName());
//        return "hero/create-hero";
//    }

    @GetMapping("/details/{name}")
    public ModelAndView details(@PathVariable String name, ModelAndView model){
        DetailsHeroServiceModel details = heroService.findHero(name);
        model.addObject("details", details);
        model.setViewName("/hero/hero-details");
        return model;
    }

    @GetMapping("/fight/{name}")
    public ModelAndView fight(@PathVariable String name, ModelAndView model, HttpSession session){
        LoginServiceModel user = (LoginServiceModel) session.getAttribute("user");
        String result = heroService.fight(user.getHeroName(), name);
        HeroModel home = heroService.findNameAndGenderHero(user.getHeroName());
        HeroModel guest = heroService.findNameAndGenderHero(name);
        model.addObject("home", home);
        model.addObject("guest", guest);
        model.addObject("result", result);
        model.setViewName("/hero/fight");
        return model;
    }
}
