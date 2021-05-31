package com.project.diploma.web.models;

import com.project.diploma.data.models.Slot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailsItemModel {

    private String name;

    private Slot slot;

    private Integer level;

    private String itemPicture;

    private Integer stamina;

    private Integer strength;

    private Integer attack;

    private Integer defence;
}
