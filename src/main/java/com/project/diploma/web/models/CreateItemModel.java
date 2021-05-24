package com.project.diploma.web.models;

import com.project.diploma.validations.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@NoArgsConstructor
public class CreateItemModel {

    @UsernameValidation
    @ItemNameAvailabilityValidation
    private String name;

    @NotEmpty(message = "Slot cannot be empty")
    private String slot;

    @NotEmpty(message = "Buy cannot be empty")
    private String buy;

    @NumberValidation
    private Integer stamina;

    @NumberValidation
    private Integer strength;

    @NumberValidation
    private Integer attack;

    @NumberValidation
    private Integer defence;

    @PositiveOrZero(message = "Price cannot be negative or zero")
    private Integer priceInGold;

    @PositiveOrZero(message = "Price cannot be negative or zero")
    private Integer priceInMoney;
}
