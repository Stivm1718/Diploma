package com.project.diploma.web.controllers;

import com.project.diploma.services.models.CreateAchievementServiceModel;
import com.project.diploma.services.services.AchievementService;
import com.project.diploma.web.models.CreateAchievementModel;
import com.project.diploma.web.models.DetailsAchievementModel;
import com.project.diploma.web.models.NameModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    private final ModelMapper mapper;
    private final AchievementService achievementService;

    @Autowired
    public AchievementController(ModelMapper mapper, AchievementService achievementService) {
        this.mapper = mapper;
        this.achievementService = achievementService;
    }

    @ModelAttribute("achievement")
    public CreateAchievementModel achievement(){
        return new CreateAchievementModel();
    }

    @GetMapping("/create")
    public String createAchievement(@ModelAttribute("achievement") CreateAchievementModel model){
        return "achievements/create";
    }

    @PostMapping("/create")
    public String achievementConfirm(@Valid @ModelAttribute("achievement") CreateAchievementModel model,
                                     BindingResult result){
        if (result.hasErrors()){
            return "achievements/create";
        }

        CreateAchievementServiceModel serviceModel = mapper.map(model, CreateAchievementServiceModel.class);
        if (achievementService.createAchievement(serviceModel)){
            return "redirect:/home";
        } else {
            return "redirect:/achievement/create";
        }
    }

    @ModelAttribute("name")
    public NameModel modelName() {
        return new NameModel();
    }

    @GetMapping("/details")
    public ModelAndView details(ModelAndView model,
                                HttpSession session,
                                @ModelAttribute("name") NameModel nameModel){
        String username = (String) session.getAttribute("username");
        List<DetailsAchievementModel> achievements = achievementService.getAchievements(username);
        model.addObject("achievements", achievements);
        model.setViewName("/achievements/details");
        return model;
    }

    @PostMapping("/details")
    public String detailsConfirm(HttpSession session,
                                 @Valid @ModelAttribute("name") NameModel nameModel){
        String username = (String) session.getAttribute("username");
        achievementService.takePrizeAndAddToUser(username, nameModel.getName());
        return "redirect:/achievements/details";
    }
}
