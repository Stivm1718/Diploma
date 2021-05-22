package com.project.diploma.services.models;

import com.project.diploma.data.models.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHeroServiceModel {

    private String name;

    private Gender gender;
}
