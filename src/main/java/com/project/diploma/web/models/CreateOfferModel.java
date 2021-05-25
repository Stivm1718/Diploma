package com.project.diploma.web.models;

import com.project.diploma.validations.NumberValidation;
import com.project.diploma.validations.OfferNameAvailabilityValidation;
import com.project.diploma.validations.OfferNameValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOfferModel {

    @OfferNameValidation
    @OfferNameAvailabilityValidation
    private String name;

    @NumberValidation
    private Integer gold;

    @NumberValidation
    private Integer price;
}
