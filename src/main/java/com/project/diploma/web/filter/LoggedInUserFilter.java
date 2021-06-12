package com.project.diploma.web.filter;

import com.project.diploma.services.services.AuthenticatedUserService;
import com.project.diploma.services.services.UserService;
import com.project.diploma.web.models.LoggedUserFilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoggedInUserFilter implements Filter {

    private final AuthenticatedUserService authenticatedUserService;
    private final UserService userService;

    @Autowired
    public LoggedInUserFilter(AuthenticatedUserService authenticatedUserService,
                              UserService userService) {
        this.authenticatedUserService = authenticatedUserService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String username = authenticatedUserService.getUsername();

        if (username.equals("anonymousUser")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        LoggedUserFilterModel logUser = userService.findLogUser(username);

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        session.setAttribute("username", username);
        session.setAttribute("authorities", logUser);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
