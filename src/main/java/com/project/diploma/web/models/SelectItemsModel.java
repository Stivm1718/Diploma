package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SelectItemsModel {

    private String name;

    @NotNull
    private String game;

    private String gauntlets;

    private String itemPictureGauntlets;

    private String helmet;

    private String itemPictureHelmet;

    private String pauldron;

    private String itemPicturePauldron;

    private String pads;

    private String itemPicturePads;

    private String weapon;

    private String itemPictureWeapon;
}
