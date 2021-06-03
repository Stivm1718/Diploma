package com.project.diploma.data.models;

import com.project.diploma.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "heroes")
@Getter
@Setter
@NoArgsConstructor
public class Hero extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "hero_picture", nullable = false)
    private String heroPicture;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "current_points")
    private Integer currentPoints;

    @Column(name = "max_points")
    private Integer maxPoints;

    @Column(name = "stamina", nullable = false)
    private Integer stamina;

    @Column(name = "strength", nullable = false)
    private Integer strength;

    @Column(name = "attack", nullable = false)
    private Integer attack;

    @Column(name = "defence", nullable = false)
    private Integer defence;

    @Column(name = "battles_with_player")
    private Integer battlesWithPlayer;

    @Column(name = "wins_vs_player")
    private Integer winsVSPlayer;

    @Column(name = "battles_with_bot")
    private Integer battlesWithBot;

    @Column(name = "wins_vs_bot")
    private Integer winsVSBot;

    @Column(name = "battles_with_friend")
    private Integer battlesWithFriend;

    @Column(name = "wins_vs_friend")
    private Integer winsVSFriend;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "heroes")
    private List<Item> items;
}
