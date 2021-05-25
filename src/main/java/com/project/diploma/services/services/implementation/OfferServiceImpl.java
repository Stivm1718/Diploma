package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.Offer;
import com.project.diploma.data.repositories.OfferRepository;
import com.project.diploma.services.models.CreateOfferServiceModel;
import com.project.diploma.services.services.OfferService;
import com.project.diploma.services.services.ValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    private final ModelMapper mapper;
    private final ValidationService validationService;
    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(ModelMapper mapper, ValidationService validationService, OfferRepository offerRepository) {
        this.mapper = mapper;
        this.validationService = validationService;
        this.offerRepository = offerRepository;
    }

    @Override
    public boolean createOffer(CreateOfferServiceModel model) {
        if (model == null){
            throw new RuntimeException("Model does not exists");
        }

        if (validationService.isValidOfferName(model)){
            return false;
        }

        offerRepository.saveAndFlush(mapper.map(model, Offer.class));
        return true;
    }
}
