package com.project.diploma.web.models;

import com.project.diploma.validations.*;
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
    @EmailAvailabilityValidation
    private String email;

    @PasswordValidation
    private String password;

    @PasswordValidation
    private String confirmPassword;
}
