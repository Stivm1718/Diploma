package com.project.diploma.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserServiceModel {

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private Set<RoleServiceModel> authorities;
}
