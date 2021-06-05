package com.project.diploma.web.models;

import com.project.diploma.validations.AchievementAvailabilityValidation;
import com.project.diploma.validations.AchievementValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CreateAchievementModel {

    @AchievementValidation
    @AchievementAvailabilityValidation
    private String name;

    @NotNull(message = "The target cannot be null")
    private Integer target;

    @NotNull(message = "The prize cannot be null")
    private Integer prize;
}
