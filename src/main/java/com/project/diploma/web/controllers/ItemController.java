package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Pay;
import com.project.diploma.services.models.CreateItemServiceModel;
import com.project.diploma.services.services.ItemService;
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

    private final ModelMapper mapper;
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public ItemController(ModelMapper mapper, ItemService itemService, UserService userService) {
        this.mapper = mapper;
        this.itemService = itemService;
        this.userService = userService;
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
        //todo: Да redirect-ва към същата страница
        CreateItemServiceModel item = this.mapper.map(model, CreateItemServiceModel.class);
        if (itemService.create(item)) {
            return "redirect:/home";
        } else {
            return "redirect:/items/create";
        }
    }

    @ModelAttribute("itemName")
    public ItemNameModel modelName() {
        return new ItemNameModel();
    }

    @ModelAttribute("items")
    public SelectItemsModel selectModel() {
        return new SelectItemsModel();
    }

    @GetMapping("/merchant/{name}")
    public ModelAndView merchant(@PathVariable String name,
                                 ModelAndView model,
                                 HttpSession session,
                                 @ModelAttribute("itemName") ItemNameModel modelName) {
        LoggedUserFilterModel roles = (LoggedUserFilterModel) session.getAttribute("authorities");
        model.addObject("heroName", name);
        int countRoles = roles.getAuthorities().size();
        if (countRoles == 2) {
            List<ViewItemModel> items = itemService.takeAllItemsThatAreNotThere(name);
            model.addObject("items", items);
        } else {
            List<ViewItemModelWithTypePay> itemsWithGold = itemService.takeItemWithGoldForPay(name);
            model.addObject("itemsWithGold", itemsWithGold);
            List<ViewItemModelWithTypePay> itemsWithMoney = itemService.takeItemWithMoneyForPay(name);
            model.addObject("itemsWithMoney", itemsWithMoney);
        }
        model.setViewName("/items/merchant");
        return model;
    }

    @PostMapping("/merchant/{heroName}")
    public String merchantConfirm(@PathVariable String heroName,
                                  HttpSession session,
                                  @Valid @ModelAttribute("itemName") ItemNameModel modelName) throws Exception {
        LoggedUserFilterModel roles = (LoggedUserFilterModel) session.getAttribute("authorities");
        int countRoles = roles.getAuthorities().size();
        if (countRoles == 2) {
            itemService.addHeroItemForAdmin(heroName, modelName.getName());
        } else {
            Pay pay = itemService.getWayToPay(modelName.getName());
            if (pay.name().equals("GOLD")) {
                if (!itemService.buyItemWithGold(heroName, modelName.getName())) {
                    session.setAttribute("text", "You don't have enough gold!");
                } else {
                    session.setAttribute("text", null);
                    String username = (String) session.getAttribute("username");
                    int gold = userService.takeGoldFromUser(username);
                    session.setAttribute("gold", gold);
                }
            } else {
                HeroItemModel heroItemModel = new HeroItemModel(heroName, modelName.getName());
                session.setAttribute("heroItemModel", heroItemModel);
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
        session.setAttribute("selectedItems", model);
        return "redirect:/heroes/opponent/" + model.getName();
    }
}
