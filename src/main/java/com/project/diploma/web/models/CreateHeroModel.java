package com.project.diploma.web.models;

import com.project.diploma.validations.UsernameAvailabilityValidation;
import com.project.diploma.validations.UsernameValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class CreateHeroModel {

    @UsernameValidation
    @UsernameAvailabilityValidation
    private String name;

    @NotEmpty(message = "Gender cannot be null")
    private String gender;
}
