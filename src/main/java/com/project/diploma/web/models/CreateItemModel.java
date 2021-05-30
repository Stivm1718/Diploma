package com.project.diploma.web.models;

import com.project.diploma.validations.ItemNameAvailabilityValidation;
import com.project.diploma.validations.UsernameValidation;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CreateItemModel {

    @UsernameValidation
    @ItemNameAvailabilityValidation
    private String name;

    @NotNull(message = "Slot cannot be empty")
    private String slot;

    @NotNull(message = "Slot cannot be empty")
    private String itemPicture;

    @NotNull(message = "Buy cannot be empty")
    private String pay;

    @NotNull(message = "The stamina cannot be null")
    private Integer stamina;

    @NotNull(message = "The strength cannot be null")
    private Integer strength;

    @NotNull(message = "The attack cannot be null")
    private Integer attack;

    @NotNull(message = "The defence cannot be null")
    private Integer defence;

    @Nullable
    private Integer priceInGold;

    @Nullable
    private Integer priceInMoney;
}
