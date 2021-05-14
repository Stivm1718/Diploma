package com.project.diploma.web.models;

import com.project.diploma.validation.UsernameValidation;
import com.project.diploma.validation.NumberValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class CreateItemModel {

    @UsernameValidation
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
}
