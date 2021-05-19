package com.project.diploma.service.models;

import com.project.diploma.data.models.Gender;
import com.project.diploma.data.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailsHeroModel {
    private String name;

    private Gender gender;

    private Integer level;

    private Integer currentPoints;

    private Integer maxPoints;

    //private Integer stamina;

    //private Integer strength;

    //private Integer attack;

    //private Integer defence;

    private List<Item> weapons;

    private List<Item> helmets;

    private List<Item> pauldrons;

    private List<Item> pads;

    private List<Item> gauntlets;
}
