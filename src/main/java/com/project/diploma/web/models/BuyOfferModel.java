package com.project.diploma.web.models;

import com.project.diploma.validations.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BuyOfferModel {

    @CardNumberValidation
    private String cardNumber;

    @CardholderValidation
    private String cardholder;

    @MonthValidation
    private Integer month;

    @YearValidation
    private Integer year;

    @CVVValidation
    private Integer cvv;
}
