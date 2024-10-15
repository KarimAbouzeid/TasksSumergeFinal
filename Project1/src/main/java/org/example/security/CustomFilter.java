package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class CustomFilter extends OncePerRequestFilter {


    private final String HEADER_TOKEN = "token";

    public CustomFilter() {
        super();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (!Collections.list(request.getHeaderNames()).contains(HEADER_TOKEN)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenHeader = request.getHeader(HEADER_TOKEN);
        String tokenCookies = Arrays.stream(request.getCookies()).map(Cookie::getName).filter((name -> name.equalsIgnoreCase("admin"))).findFirst().orElse("");
        System.out.println(tokenCookies);
        if ((tokenHeader == null || !tokenHeader.equals("admin")) && !tokenCookies.equals("admin")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type", "text/plain;charset=UTF-8");
            response.getWriter().print("You are not an admin");
            return;
        }
        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(
                new AdminAuthentication()
        );
        SecurityContextHolder.setContext(newContext);
        filterChain.doFilter(request, response);
    }





}
