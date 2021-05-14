package com.project.diploma.service.models;

import com.project.diploma.data.models.Hero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileServiceModel {
    private String username;

    private String email;

    private Hero hero;

    public ProfileServiceModel(String username, String email, Hero hero) {
        this.username = username;
        this.email = email;
        this.hero = hero;
    }
}
