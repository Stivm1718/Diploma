package com.project.diploma.web.models;

import com.project.diploma.data.models.Item;
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

    private List<Item> weapons = new ArrayList<>();

    private List<Item> helmets = new ArrayList<>();

    private List<Item> pauldrons = new ArrayList<>();

    private List<Item> pads = new ArrayList<>();

    private List<Item> gauntlets = new ArrayList<>();
}
