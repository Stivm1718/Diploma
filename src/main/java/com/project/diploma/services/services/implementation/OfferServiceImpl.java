package com.project.diploma.services.services.implementation;

import com.project.diploma.data.models.Offer;
import com.project.diploma.data.repositories.OfferRepository;
import com.project.diploma.services.models.CreateOfferServiceModel;
import com.project.diploma.services.services.OfferService;
import com.project.diploma.services.services.ValidationService;
import com.project.diploma.web.models.ViewOffer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (validationService.isValidOfferName(model)) {
            return false;
        }

        offerRepository.saveAndFlush(mapper.map(model, Offer.class));
        return true;
    }

    @Override
    public List<ViewOffer> getAllOffers() {
        return offerRepository
                .findAll()
                .stream()
                .map(o -> mapper.map(o, ViewOffer.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOffer(String name) {
        Offer offer = offerRepository.getOfferByName(name);
        offerRepository.delete(offer);
    }

    @Override
    public boolean existOffer(String name) {
        return offerRepository.existsOfferByName(name);
    }
}
