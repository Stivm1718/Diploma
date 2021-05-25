package com.project.diploma.web.models;

import com.project.diploma.data.models.Pay;
import com.project.diploma.data.models.Slot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ViewItemModelWithTypePay {

    private String name;

    private Slot slot;

    private Pay buy;

    private Integer stamina;

    private Integer strength;

    private Integer attack;

    private Integer defence;

    private Integer priceInGold;

    private Integer priceInMoney;
}
