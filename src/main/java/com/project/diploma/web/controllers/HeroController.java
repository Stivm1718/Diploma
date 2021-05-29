package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Gender;
import com.project.diploma.services.models.CreateHeroServiceModel;
import com.project.diploma.services.services.HeroService;
import com.project.diploma.services.services.ItemService;
import com.project.diploma.web.models.*;
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
    private final ItemService itemService;

    @Autowired
    public HeroController(HeroService heroService, ModelMapper mapper, ItemService itemService) {
        this.heroService = heroService;
        this.mapper = mapper;
        this.itemService = itemService;
    }

    @ModelAttribute("hero")
    public CreateHeroModel model() {
        return new CreateHeroModel();
    }

    @GetMapping("/create")
    public String viewHeroPage(@ModelAttribute("hero") CreateHeroModel model) {
        return "heroes/create";
    }

    @PostMapping("/create")
    public String createHero(@Valid @ModelAttribute("hero") CreateHeroModel model,
                             BindingResult result,
                             Principal principal) throws Exception {
        if (result.hasErrors()) {
            return "heroes/create";
        }

        String username = principal.getName();

        CreateHeroServiceModel hero = mapper.map(model, CreateHeroServiceModel.class);
        hero.setGender(Gender.valueOf(model.getGender().toUpperCase()));
        if (heroService.createHero(hero, username)) {
            return "redirect:/home";
        } else {
            return "redirect:/heroes/create";
        }
    }

    @GetMapping("/select")
    public ModelAndView selectHero(ModelAndView model, Principal principal) {
        String username = principal.getName();
        int count = heroService.getCountOfHeroes(username);
        model.addObject("countHeroes", count);
        if (count != 0) {
            List<HeroPictureModel> heroes = heroService.getAllUserHeroes(username);
            model.addObject("heroes", heroes);
        }
        model.setViewName("/heroes/select");
        return model;
    }

    @GetMapping("/opponent/{name}")
    public String selectOpponent(@PathVariable String name, HttpSession session, Principal principal) {
        String username = principal.getName();
        HeroPictureModel opponent = heroService.selectOpponent(username, name);
        session.setAttribute("opponent", opponent);
        if (opponent == null) {
            return "heroes/opponent";
        }

        SelectItemsModel selectItemsModel = itemService.getTheBestItemsOfOpponent(opponent.getName());
        selectItemsModel.setName(opponent.getName());
        session.setAttribute("itemsOfOpponent", selectItemsModel);

        HeroPictureModel model = heroService.getMyHero(name);
        session.setAttribute("myHero", model);
        return "heroes/opponent";
    }

    @GetMapping("/details/{name}")
    public ModelAndView details(@PathVariable String name, ModelAndView model) {
        DetailsHeroModel details = heroService.detailsHero(name);
        model.addObject("details", details);
        model.setViewName("/heroes/details");
        return model;
    }

    @GetMapping("/fight")
    public ModelAndView fight(ModelAndView model, HttpSession session) {
        HeroPictureModel myHero = (HeroPictureModel) session.getAttribute("myHero");
        HeroPictureModel opponent = (HeroPictureModel) session.getAttribute("opponent");
        SelectItemsModel myItems = (SelectItemsModel) session.getAttribute("selectedItems");
        SelectItemsModel opponentItems = (SelectItemsModel) session.getAttribute("itemsOfOpponent");

        BattleModel battleModel = heroService.fight(myHero, opponent, myItems, opponentItems);
        session.setAttribute("battleResult", battleModel);
        model.setViewName("/heroes/fight");
        return model;
    }

    @GetMapping("/ranking")
    public ModelAndView ranking(ModelAndView model) {
        List<RankingModel> heroes = heroService.getSortedHeroes();
        model.addObject("sortedHeroes", heroes);
        model.setViewName("/heroes/ranking");
        return model;
    }
}
