package com.project.diploma.services.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAchievementServiceModel {

    private String name;

    private Integer target;

    private Integer prize;
}
