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

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "stamina", nullable = false)
    private Integer stamina;

    @Column(name = "strength", nullable = false)
    private Integer strength;

    @Column(name = "attack", nullable = false)
    private Integer attack;

    @Column(name = "defence", nullable = false)
    private Integer defence;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "heroes")
    private List<Item> items;
}
