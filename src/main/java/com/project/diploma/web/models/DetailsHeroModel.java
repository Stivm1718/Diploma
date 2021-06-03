package com.project.diploma.web.models;

import com.project.diploma.data.models.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailsHeroModel {
    private String name;

    private Gender gender;

    private String heroPicture;

    private Integer level;

    private Integer currentPoints;

    private Integer maxPoints;

    private Integer battlesWithPlayer;

    private Integer winsVSPlayer;

    private Integer battlesWithBot;

    private Integer winsVSBot;

    private Integer battlesWithFriend;

    private Integer winsVSFriend;

    private List<DetailsItemModel> weapons = new ArrayList<>();

    private List<DetailsItemModel> helmets = new ArrayList<>();

    private List<DetailsItemModel> pauldrons = new ArrayList<>();

    private List<DetailsItemModel> pads = new ArrayList<>();

    private List<DetailsItemModel> gauntlets = new ArrayList<>();
}
