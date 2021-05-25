package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HeroItemModel {

    private String nameHero;

    private String nameItem;

    public HeroItemModel(String nameHero, String nameItem){
        this.nameHero = nameHero;
        this.nameItem = nameItem;
    }
}
