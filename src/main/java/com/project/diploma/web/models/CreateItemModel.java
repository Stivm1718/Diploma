package com.project.diploma.web.models;

import com.project.diploma.validation.UsernameAvailabilityValidation;
import com.project.diploma.validation.UsernameValidation;
import com.project.diploma.validation.NumberValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@NoArgsConstructor
public class CreateItemModel {

    @UsernameValidation
    @UsernameAvailabilityValidation
    private String name;

    @NotEmpty(message = "Slot cannot be empty")
    private String slot;

    @NumberValidation
    private Integer stamina;

    @NumberValidation
    private Integer strength;

    @NumberValidation
    private Integer attack;

    @NumberValidation
    private Integer defence;

    @Positive(message = "Price cannot be negative or zero")
    @NotNull(message = "Price cannot be null")
    private Integer price;
}
