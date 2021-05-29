package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RankingModel {

    private Integer position;

    private String name;

    private String heroPicture;

    private Integer level;
}
