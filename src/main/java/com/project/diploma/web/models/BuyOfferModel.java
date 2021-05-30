package com.project.diploma.web.models;

import com.project.diploma.validations.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class BuyOfferModel {

    @CardNumberValidation
    private String cardNumber;

    @CardholderValidation
    private String cardholder;

    @NotNull(message = "The month cannot be null.")
    private Integer month;

    @NotNull(message = "The year cannot be null.")
    private Integer year;

    @NotNull(message = "The year cannot be null.")
    private Integer cvv;
}
