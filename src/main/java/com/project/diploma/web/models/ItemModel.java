package com.project.diploma.web.models;

import com.project.diploma.data.models.Slot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemModel {

    private String name;

    private Slot slot;

    private Integer stamina;

    private Integer strength;

    private Integer attack;

    private Integer defence;
}
