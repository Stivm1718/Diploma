package com.project.diploma.web.controllers;

import com.project.diploma.service.services.ItemService;
import com.project.diploma.web.models.CreateItemModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ModelMapper mapper;
    private final ItemService itemService;

    public ItemController(ModelMapper mapper, ItemService itemService) {
        this.mapper = mapper;
        this.itemService = itemService;
    }

    @ModelAttribute("item")
    public CreateItemModel model(){
        return new CreateItemModel();
    }

    @GetMapping("/create")
    public String createItem(@ModelAttribute("item") CreateItemModel model){
        return "item/create-item";
    }

//    @PostMapping("/create")
//    public String createItemConfirm(@Valid @ModelAttribute("item") CreateItemModel model, BindingResult result) throws Exception {
//        if (result.hasErrors()){
//            return "item/create-item";
//        }
//        CreateItemServiceModel item = this.mapper.map(model, CreateItemServiceModel.class);
//        item.setSlot(Slot.valueOf(model.getSlot()));
//        this.itemService.create(item);
//        return "redirect:/home";
//    }

//    @GetMapping("/merchant")
//    public String merchant(HttpSession session){
//        LoginServiceModel user = (LoginServiceModel) session.getAttribute("user");
//        List<CreateItemServiceModel> items = itemService.getByHeroName(user.getHeroName());
//        session.setAttribute("items", items);
//        return "item/merchant";
//    }
//
//    @PostMapping("/merchant/{name}")
//    public String merchantConfirm(@PathVariable String name, HttpSession session) throws Exception {
//        LoginServiceModel user = (LoginServiceModel) session.getAttribute("user");
//        itemService.addItem(user.getHeroName(), name);
//        return "redirect:/items/merchant";
//    }
}
