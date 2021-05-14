package com.project.diploma.web.models;

import com.project.diploma.service.models.RoleServiceModel;

import java.util.Set;

public class LoggedUserFilterModel {

    private String username;

    private Set<RoleServiceModel> authorities;
}
