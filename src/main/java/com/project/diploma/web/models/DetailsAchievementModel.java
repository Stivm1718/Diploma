package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailsAchievementModel {

    private String name;

    private int target;

    private int prize;

    private int currentResult;

    private boolean isComplete;
}
