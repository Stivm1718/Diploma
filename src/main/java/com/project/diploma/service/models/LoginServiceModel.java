package com.project.diploma.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginServiceModel {

    private String username;

    private String heroName;

    public LoginServiceModel(String username, String heroName) {
        this.username = username;
        this.heroName = heroName;
    }
}
