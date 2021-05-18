//package com.project.diploma.web.filter;
//
//import com.project.diploma.service.models.DetailsHeroServiceModel;
//import com.project.diploma.service.services.AuthenticatedUserService;
//import com.project.diploma.service.services.HeroService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class UserHeroInterceptor implements HandlerInterceptor {
//
//    private final AuthenticatedUserService authenticatedUserService;
//    private final HeroService heroService;
//
//    @Autowired
//    public UserHeroInterceptor(AuthenticatedUserService authenticatedUserService, HeroService heroService) {
//        this.authenticatedUserService = authenticatedUserService;
//        this.heroService = heroService;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response,
//                           Object handler, ModelAndView modelAndView) throws Exception {
//        String username = authenticatedUserService.getUsername();
//        DetailsHeroServiceModel hero = heroService.getByUsername(username);
//        request.getSession().setAttribute("heroName", hero.getName());
//    }
//
////    @Override
////    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        String username = authenticatedUserService.getUsername();
////        DetailsHeroServiceModel hero = heroService.getByUsername(username);
////        request.getSession().setAttribute("heroName", hero.getName());
////        return true;
////    }
//}
