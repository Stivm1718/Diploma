package com.project.diploma.web.models;

import com.project.diploma.service.models.RoleServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class LoggedUserFilterModel {

    private String username;

    private Set<RoleServiceModel> authorities;
}
