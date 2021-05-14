package com.project.diploma.web.models;

import com.project.diploma.validation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsPasswordValidation
public class RegisterUserModel {

    @UsernameValidation
    @UsernameAvailabilityValidation
    private String username;

    @EmailValidation
    private String email;

    @PasswordValidation
    private String password;

    @PasswordValidation
    private String confirmPassword;
}
