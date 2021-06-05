package com.project.diploma.data.models;

import com.project.diploma.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
public class Achievement extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target", nullable = false)
    private Integer target;

    @Column(name = "prize", nullable = false)
    private Integer prize;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "achievement_users",
            joinColumns = @JoinColumn(name = "achievement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;
}
