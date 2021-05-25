package com.project.diploma.web.controllers;

import com.project.diploma.services.models.CreateOfferServiceModel;
import com.project.diploma.services.services.OfferService;
import com.project.diploma.web.models.CreateOfferModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final ModelMapper mapper;
    private final OfferService offerService;

    @Autowired
    public OfferController(ModelMapper mapper, OfferService offerService) {
        this.mapper = mapper;
        this.offerService = offerService;
    }

    @ModelAttribute("offer")
    public CreateOfferModel offer(){
        return new CreateOfferModel();
    }

    @GetMapping("/offer")
    public String createOffer(@ModelAttribute("offer") CreateOfferModel model){
        return "offers/offer";
    }

    @PostMapping("/offer")
    public String offerConfirm(@Valid @ModelAttribute("offer") CreateOfferModel model,
                               BindingResult result) throws Exception {
        if (result.hasErrors()){
            return "offers/offer";
        }

        CreateOfferServiceModel serviceModel = mapper.map(model, CreateOfferServiceModel.class);

        if (offerService.createOffer(serviceModel)){
            return "redirect:/home";
        } else {
            return "redirect:/offers/offer";
        }
    }
}
