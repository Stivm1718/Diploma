package com.project.diploma.web.controllers;

import com.project.diploma.data.models.Slot;
import com.project.diploma.service.models.CreateItemServiceModel;
import com.project.diploma.service.services.ItemService;
import com.project.diploma.web.models.CreateItemModel;
import com.project.diploma.web.models.ViewItemModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        item.setSlot(Slot.valueOf(model.getSlot()));
        if (itemService.create(item)) {
            return "redirect:/home";
        } else {
            return "redirect:/items/create";
        }
    }

    @GetMapping("/merchant/{name}")
    public ModelAndView merchant(@PathVariable String name, ModelAndView model) {
        List<ViewItemModel> items = itemService.takeAllItemsThatAreNotThere(name);
        model.addObject("items", items);
        model.setViewName("/items/merchant");
        return model;
    }
//
//    @PostMapping("/merchant/{name}")
//    public String merchantConfirm(@PathVariable String name, HttpSession session) throws Exception {
//        LoginServiceModel user = (LoginServiceModel) session.getAttribute("user");
//        itemService.addItem(user.getHeroName(), name);
//        return "redirect:/items/merchant";
//    }
}
