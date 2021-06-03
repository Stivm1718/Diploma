package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BattleModel {

    private String result;

    private String game;

    private Integer stamina;

    private Integer defence;

    private Integer strength;

    private Integer attack;

    private Integer gold;

    private Integer pointsEarned;

    private Boolean isRaisedLevel;

    private Integer level;

    private Integer myAttack;

    private Integer myDefence;

    private Integer myStrength;

    private Integer myStamina;

    private Integer myResult;

    private Integer opponentAttack;

    private Integer opponentDefence;

    private Integer opponentStrength;

    private Integer opponentStamina;

    private Integer opponentResult;
}
