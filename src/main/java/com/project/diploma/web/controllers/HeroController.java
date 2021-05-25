package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Gender;
import com.project.diploma.data.models.Hero;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.web.models.CreateHeroModel;
import com.project.diploma.web.models.DetailsHeroModel;
import com.project.diploma.web.models.HeroModel;
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

@Controller
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;
    private final ModelMapper mapper;

    @Autowired
    public HeroController(HeroService heroService, ModelMapper mapper) {
        this.heroService = heroService;
        this.mapper = mapper;
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
    public String createHero(@Valid @ModelAttribute("hero") CreateHeroModel model,
                             BindingResult result,
                             Principal principal) throws Exception {
        if (result.hasErrors()){
            return "heroes/create";
        }

        String username = principal.getName();

        CreateHeroServiceModel hero = mapper.map(model, CreateHeroServiceModel.class);
        hero.setGender(Gender.valueOf(model.getGender().toUpperCase()));
        if(heroService.createHero(hero, username)){
            return "redirect:/home";
        } else{
            return "redirect:/heroes/create";
        }
    }

    @GetMapping("/select")
    public ModelAndView selectHero(ModelAndView model, Principal principal){
        String username = principal.getName();
        long count = heroService.getCountOfHeroes(username);
        model.addObject("countHeroes", count);
        if(count != 0){
            List<Hero> heroes = heroService.getAllUserHeroes(username);
            model.addObject("heroes", heroes);
        }
        model.setViewName("/heroes/select");
        return model;
    }

    @GetMapping("/opponent/{name}")
    public String selectOpponent(@PathVariable String name, HttpSession session, Principal principal){
        String username = principal.getName();
        HeroModel opponent = heroService.selectOpponent(username, name);
        session.setAttribute("opponent", opponent);
        if (opponent == null){
            return "heroes/opponent";
        }

        HeroModel model = heroService.getHero(name);
        session.setAttribute("myHero", model);
        return "heroes/fight";
    }

    @GetMapping("/details/{name}")
    public ModelAndView details(@PathVariable String name, ModelAndView model){
        DetailsHeroModel details = heroService.detailsHero(name);
        model.addObject("details", details);
        model.setViewName("/heroes/details");
        return model;
    }

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
