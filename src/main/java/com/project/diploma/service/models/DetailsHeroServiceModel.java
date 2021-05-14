package com.project.diploma.service.models;

import com.project.diploma.data.models.Gender;
import com.project.diploma.data.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailsHeroServiceModel {
    private String name;

    private Gender gender;

    private Integer level;

    private Integer stamina;

    private Integer strength;

    private Integer attack;

    private Integer defence;

    private Item weapon;

    private Item helmet;

    private Item pauldron;

    private Item pads;

    private Item gauntlets;
}
