package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Buy;
import com.project.diploma.data.models.Slot;
import com.project.diploma.services.models.CreateItemServiceModel;
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
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ModelMapper mapper;
    private final ItemService itemService;

    @Autowired
    public ItemController(ModelMapper mapper, ItemService itemService) {
        this.mapper = mapper;
        this.itemService = itemService;
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
        //item.setSlot(Slot.valueOf(model.getSlot()));
        //item.setBuy(Buy.valueOf(model.getBuy()));
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
    public SelectItemsModel selectModel(){
        return new SelectItemsModel();
    }

    @GetMapping("/merchant/{name}")
    public ModelAndView merchant(@PathVariable String name,
                                 ModelAndView model,
                                 @ModelAttribute("itemName") ItemNameModel modelName) {
        List<ViewItemModel> items = itemService.takeAllItemsThatAreNotThere(name);
        model.addObject("items", items);
        model.addObject("heroName", name);
        model.setViewName("/items/merchant");
        return model;
    }

    @PostMapping("/merchant/{heroName}")
    public String merchantConfirm(@PathVariable String heroName,
                                        HttpSession session,
                                        @Valid @ModelAttribute("itemName") ItemNameModel modelName) throws Exception {
        //todo Да направя логиката и за потребителя
        boolean isBuyItem = itemService.addItemToHero(heroName, modelName.getName());
        if (!isBuyItem) {
            session.setAttribute("shop", "You don't have enough gold. Go to the store and buy");
        }
        return "redirect:/items/merchant/" + heroName;
    }

    @ModelAttribute("items")
    public SelectItemsModel getItems(){
        return new SelectItemsModel();
    }

    @GetMapping("/select/{name}")
    public ModelAndView getSelectItems(@PathVariable String name,
                                       ModelAndView modelAndView,
                                       @ModelAttribute("items") SelectItemsModel model){

        ShowItemsHero showItemsHero = itemService.getItemsOfHero(name);
        modelAndView.addObject("select", showItemsHero);
        modelAndView.setViewName("/items/select");
        return modelAndView;
        //todo да направя бек-енд-а
    }

    @PostMapping("/select")
    public String selectedItems(@ModelAttribute("items") SelectItemsModel model,
                                HttpSession session) throws Exception {
        session.setAttribute("selectedItems", model);
        return "redirect:/heroes/opponent/" + model.getName();
    }
}
