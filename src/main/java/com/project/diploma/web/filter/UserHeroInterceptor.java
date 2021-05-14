package com.project.diploma.web.filter;

import com.project.diploma.error.HeroNotFoundException;
import com.project.diploma.service.models.DetailsHeroServiceModel;
import com.project.diploma.service.services.AuthenticatedUserService;
import com.project.diploma.service.services.HeroService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserHeroInterceptor implements HandlerInterceptor {

    private final AuthenticatedUserService authenticatedUserService;
    private final HeroService heroService;

    public UserHeroInterceptor(AuthenticatedUserService authenticatedUserService, HeroService heroService) {
        this.authenticatedUserService = authenticatedUserService;
        this.heroService = heroService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        String username = authenticatedUserService.getUsername();
        try {
            DetailsHeroServiceModel hero = heroService.getByUsername(username);
            request.getSession()
                    .setAttribute("heroName", hero.getName());
        } catch (HeroNotFoundException ex) {
            // do nothing
        }
    }
}
