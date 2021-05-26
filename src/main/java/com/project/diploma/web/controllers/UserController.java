package com.project.diploma.web.controllers;

import com.project.diploma.services.models.RegisterUserServiceModel;
import com.project.diploma.services.services.ItemService;
import com.project.diploma.services.services.OfferService;
import com.project.diploma.services.services.UserService;
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

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;
    private final ItemService itemService;
    private final OfferService offerService;

    @Autowired
    public UserController(ModelMapper mapper, UserService userService, ItemService itemService, OfferService offerService) {
        this.mapper = mapper;
        this.userService = userService;
        this.itemService = itemService;
        this.offerService = offerService;
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
    public String confirmRegister(@Valid @ModelAttribute("register") RegisterUserModel model,
                                  BindingResult result) {
        if (result.hasErrors()) {
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
    public String loginConfirm(@ModelAttribute("login") LoginUserModel model,
                               HttpSession session) {

        session.setAttribute("user", model.getUsername().equals("") ? "Username can not be empty." : "");

        session.setAttribute("pass", model.getPassword().equals("") ? "Password can not be empty." : "");

        session.setAttribute("error", !model.getPassword().equals("") &&
                !model.getUsername().equals("") ? "Incorrect username or password." : "");

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        ProfileUserModel user = userService.getDetailsForUser(username);
        modelAndView.addObject("profile", user);
        modelAndView.setViewName("/users/profile");
        return modelAndView;
    }

    @ModelAttribute("payment")
    public BuyOfferModel buy() {
        return new BuyOfferModel();
    }

    @GetMapping("/payment")
    public String buyOffer(@ModelAttribute("payment") BuyOfferModel model) {
        return "users/payment";
    }

    @PostMapping("/payment")
    public String buyConfirm(@Valid @ModelAttribute("payment") BuyOfferModel model,
                             BindingResult result,
                             HttpSession session) throws Exception {
        if (result.hasErrors()) {
            return "users/payment";
        }
        //todo Да redirect-ва към същата страница

        HeroItemModel heroItemModel = (HeroItemModel) session.getAttribute("heroItemModel");
        if (heroItemModel != null){
            itemService.addHeroItemForAdmin(heroItemModel.getNameHero(), heroItemModel.getNameItem());
            return "redirect:/users/success-added-item";
        } else {
            UserOfferHeroModel userOfferModel =
                    (UserOfferHeroModel) session.getAttribute("userOfferHeroModel");
            userService.addGoldToUser(userOfferModel.getUsername(), userOfferModel.getOfferName());
            return "redirect:/users/success-bought-gold";
        }
    }

    @GetMapping("/success-added-item")
    public String successAddedItem(){
        return "users/success-added-item";
    }

    @GetMapping("/success-bought-gold")
    public String successBoughtGold(){
        return "users/success-bought-gold";
    }
}
