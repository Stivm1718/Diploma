package com.project.diploma.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/users/register", "/users/login").permitAll()
                .antMatchers("/js/*", "/css/*", "/img/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home", true)
                .failureForwardUrl("/users/login?error=true")
                .and()
                .logout()
                .logoutSuccessUrl("/users/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");
    }
}
