package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShowItemsHero {

    private String name;

    private List<DetailsItemModel> weapons = new ArrayList<>();

    private List<DetailsItemModel> helmets = new ArrayList<>();

    private List<DetailsItemModel> pauldrons = new ArrayList<>();

    private List<DetailsItemModel> pads = new ArrayList<>();

    private List<DetailsItemModel> gauntlets = new ArrayList<>();
}
