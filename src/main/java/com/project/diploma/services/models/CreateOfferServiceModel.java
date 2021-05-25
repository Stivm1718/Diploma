package com.project.diploma.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateOfferServiceModel {

    private String name;

    private Integer gold;

    private Integer price;
}
