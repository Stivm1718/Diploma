package com.project.diploma.web.models;

import com.project.diploma.validations.OfferNameAvailabilityValidation;
import com.project.diploma.validations.OfferNameValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CreateOfferModel {

    @OfferNameValidation
    @OfferNameAvailabilityValidation
    private String name;

    @NotNull(message = "The gold cannot be null")
    private Integer gold;

    @NotNull(message = "The price cannot be null")
    private Integer price;
}
