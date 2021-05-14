package com.project.diploma.service.models;

import com.project.diploma.validation.UsernameValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHeroServiceModel {

    @UsernameValidation
    private String name;

    @NotEmpty(message = "Gender cannot be null")
    private String gender;
}
