package com.project.diploma.services.models;

import com.project.diploma.data.models.Slot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateItemServiceModel {

    private String name;

    private Slot slot;

    private Integer stamina;

    private Integer strength;

    private Integer attack;

    private Integer defence;

    private Integer price;
}
