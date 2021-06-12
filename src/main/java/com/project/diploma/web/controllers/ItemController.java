package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Pay;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.services.HeroService;
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
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final static String GAME_WITH_PLAYER = "player";
    private final static String GAME_WITH_BOT = "bot";

    private final ModelMapper mapper;
    private final ItemService itemService;
    private final UserService userService;
    private final OfferService offerService;
    private final HeroService heroService;

    @Autowired
    public ItemController(ModelMapper mapper,
                          ItemService itemService,
                          UserService userService,
                          OfferService offerService,
                          HeroService heroService) {
        this.mapper = mapper;
        this.itemService = itemService;
        this.userService = userService;
        this.offerService = offerService;
        this.heroService = heroService;
    }

    @ModelAttribute("item")
    public CreateItemModel model() {
        return new CreateItemModel();
    }

    @GetMapping("/create")
    public String createItem(@ModelAttribute("item") CreateItemModel model) {
        return "items/create";
    }

    @PostMapping("/create")
    public String createItemConfirm(@Valid @ModelAttribute("item") CreateItemModel model,
                                    BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "items/create";
        }
        CreateItemServiceModel item = this.mapper.map(model, CreateItemServiceModel.class);
        if (itemService.create(item)) {
            return "redirect:/home";
        } else {
            return "redirect:/items/create";
        }
    }

    @ModelAttribute("name")
    public NameModel modelName() {
        return new NameModel();
    }

    @GetMapping("/merchant/{name}")
    public ModelAndView merchant(@PathVariable String name,
                                 ModelAndView model,
                                 HttpSession session,
                                 @ModelAttribute("name") NameModel nameModel) {
        LoggedUserFilterModel roles = (LoggedUserFilterModel) session.getAttribute("authorities");
        HeroPictureModel hero = heroService.getMyHero(name);
        model.addObject("hero", hero);
        int countRoles = roles.getAuthorities().size();
        if (countRoles == 2) {
            List<ViewItemModel> items = itemService.takeAllItemsThatAreNotThere(name);
            model.addObject("items", items);
        } else {
            List<ViewItemModelWithTypePay> itemsWithGold = itemService.takeItemWithGoldForPay(name);
            model.addObject("itemsWithGold", itemsWithGold);
            List<ViewItemModelWithTypePay> itemsWithMoney = itemService.takeItemWithMoneyForPay(name);
            model.addObject("itemsWithMoney", itemsWithMoney);
            String item = (String) session.getAttribute("item");
            if (item != null) {
                model.addObject("selectItem", item);
                session.setAttribute("item", null);
            }
        }
        List<ViewOffer> offers = offerService.getAllOffers();
        model.addObject("offers", offers);
        model.setViewName("/items/merchant");
        return model;
    }

    @PostMapping("/merchant/{heroName}")
    public String merchantConfirm(@PathVariable String heroName,
                                  HttpSession session,
                                  @Valid @ModelAttribute("name") NameModel nameModel) throws Exception {
        LoggedUserFilterModel roles = (LoggedUserFilterModel) session.getAttribute("authorities");
        int countRoles = roles.getAuthorities().size();
        if (countRoles == 2) {
            if (itemService.existItem(nameModel.getName())){
                itemService.addHeroItemForAdmin(heroName, nameModel.getName());
            } else if (offerService.existOffer(nameModel.getName())){
                offerService.deleteOffer(nameModel.getName());
            } else {
                itemService.deleteItem(nameModel.getName());
            }
        } else {
            session.setAttribute("myHeroName", heroName);
            if (itemService.existItem(nameModel.getName())) {
                Pay pay = itemService.getWayToPay(nameModel.getName());
                if (pay.name().equals("GOLD")) {
                    if (!itemService.buyItemWithGold(heroName, nameModel.getName())) {
                        session.setAttribute("item", nameModel.getName());
                    } else {
                        String username = (String) session.getAttribute("username");
                        int gold = userService.takeGoldFromUser(username);
                        session.setAttribute("gold", gold);
                    }
                } else {
                    HeroItemModel heroItemModel = new HeroItemModel(heroName, nameModel.getName());
                    session.setAttribute("heroItemModel", heroItemModel);
                    session.setAttribute("userOfferHeroModel", null);
                    return "redirect:/users/payment";
                }
            } else {
                String username = (String) session.getAttribute("username");
                UserOfferHeroModel model = new UserOfferHeroModel(username,
                        nameModel.getName(),
                        heroName);
                session.setAttribute("userOfferHeroModel", model);
                session.setAttribute("heroItemModel", null);
                return "redirect:/users/payment";
            }
        }
        return "redirect:/items/merchant/" + heroName;
    }

    @ModelAttribute("items")
    public SelectItemsModel getItems() {
        return new SelectItemsModel();
    }

    @GetMapping("/select/{name}")
    public ModelAndView getSelectItems(@PathVariable String name,
                                       ModelAndView modelAndView,
                                       @ModelAttribute("items") SelectItemsModel model) {
        ShowItemsHero showItemsHero = itemService.getItemsOfHero(name);
        modelAndView.addObject("select", showItemsHero);
        modelAndView.setViewName("/items/select");
        return modelAndView;
    }

    @PostMapping("/select")
    public String selectedItems(@ModelAttribute("items") SelectItemsModel model,
                                HttpSession session) {
        if ("friend".equals(model.getGame())) {
            if (model.getFriend() != null) {
                if (model.getFriend().equals("")) {
                    session.setAttribute("invalidName", "You must enter the name of the hero.");
                    return "redirect:/items/select/" + model.getName();
                }
                boolean isExist = heroService.isExistHero(model.getFriend());
                if (!isExist) {
                    session.setAttribute("invalidName", "The hero does not exist.");
                    return "redirect:/items/select/" + model.getName();
                }

                boolean isYoursHero = heroService.isYoursHero(model.getFriend(), model.getName());
                if (isYoursHero) {
                    session.setAttribute("invalidName", "The hero is yours.");
                    return "redirect:/items/select/" + model.getName();
                }
                session.setAttribute("invalidName", null);
            }
        }

        itemService.getNamesPictureItems(model);
        session.setAttribute("selectedItems", model);
        if (model.getGame() != null) {
            return "redirect:/heroes/opponent/" + model.getName();
        }
        return "redirect:/items/select/" + model.getName();
    }
}
