package com.project.diploma.web.filter;

import com.project.diploma.error.HeroNotFoundException;
import com.project.diploma.service.models.DetailsHeroServiceModel;
import com.project.diploma.service.services.AuthenticatedUserService;
import com.project.diploma.service.services.HeroService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthenticatedUserService authenticatedUserService;
    private final HeroService heroesService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public UserAuthenticationSuccessHandler(
            AuthenticatedUserService authenticatedUserService,
            HeroService heroesService) {
        super();
        this.authenticatedUserService = authenticatedUserService;
        this.heroesService = heroesService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        org.springframework.security.core.Authentication authentication) throws IOException, ServletException, IOException {
        String username = authenticatedUserService.getUsername();
        try {
            DetailsHeroServiceModel hero = heroesService.getByUsername(username);
            httpServletRequest.getSession()
                    .setAttribute("heroName", hero.getName());
        } catch (HeroNotFoundException ex) {
            // do nothing
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/home");
    }
}
