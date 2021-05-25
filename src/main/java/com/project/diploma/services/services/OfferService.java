package com.project.diploma.services.services;

import com.project.diploma.services.models.CreateOfferServiceModel;

public interface OfferService {
    boolean createOffer(CreateOfferServiceModel serviceModel) throws Exception;
}
