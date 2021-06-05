package com.project.diploma.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProfileUserModel {

    private String username;

    private String email;

    private Integer gold;

    private Integer totalBattlesWithPlayer;

    private Integer totalWinsVSPlayer;

    private Integer totalBattlesWithBot;

    private Integer totalWinsVSBot;

    private Integer totalBattlesWithFriend;

    private Integer totalWinsVSFriend;
}
