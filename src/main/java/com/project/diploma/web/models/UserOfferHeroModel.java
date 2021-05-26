package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserOfferHeroModel {

    private String username;

    private String offerName;

    private String heroName;

    public UserOfferHeroModel(String username, String offerName, String heroName){
        this.username = username;
        this.offerName = offerName;
        this.heroName = heroName;
    }
}
