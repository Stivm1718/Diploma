package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateOfferServiceModel;
import com.project.diploma.web.models.ViewOffer;

import java.util.List;

public interface OfferService {
    boolean createOffer(CreateOfferServiceModel serviceModel) throws Exception;

    List<ViewOffer> getAllOffers();

    void deleteOffer(String name);

    boolean existOffer(String name);
}
