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

    @ExpirationDateValidation
    private String expirationDate;

    @NotNull(message = "The cvv cannot be null.")
    private Integer cvv;
}
