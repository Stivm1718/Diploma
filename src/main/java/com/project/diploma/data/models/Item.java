package com.project.diploma.data.models;

import com.project.diploma.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "items")
@Setter
@Getter
@NoArgsConstructor
public class Item extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slot", nullable = false)
    @Enumerated(EnumType.STRING)
    private Slot slot;

    @Column(name = "stamina", nullable = false)
    private Integer stamina;

    @Column(name = "strength", nullable = false)
    private Integer strength;

    @Column(name = "attack", nullable = false)
    private Integer attack;

    @Column(name = "defence", nullable = false)
    private Integer defence;

    @ManyToMany
    @JoinTable(
            name = "items_heroes",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id", referencedColumnName = "id")
    )
    private List<Hero> heroes;
}
