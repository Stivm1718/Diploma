package com.project.diploma.data.models;

import com.project.diploma.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "offers")
@Setter
@Getter
@NoArgsConstructor
public class Offer extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gold", nullable = false)
    private Integer gold;

    @Column(name = "price", nullable = false)
    private Integer price;
}
