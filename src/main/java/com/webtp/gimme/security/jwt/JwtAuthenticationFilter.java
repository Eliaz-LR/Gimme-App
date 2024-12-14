package com.webtp.gimme.security.jwt;

import com.webtp.gimme.security.CustomerDetails;
import com.webtp.gimme.security.CustomerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = jwtService.resolveToken(request);

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null && jwtService.validateToken(jwt)) {
            String username = jwtService.getUsernameFromToken(jwt);
            CustomerDetails customerDetails = (CustomerDetails) customerDetailsService.loadUserByUsername(username);
            Authentication authentication = jwtService.getAuthentication(jwt, customerDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if ((token == null || !jwtService.validateToken(token)) && request.getHeader(HttpHeaders.USER_AGENT) != null) {
            SecurityContextHolder.clearContext();
            String requestURI = request.getRequestURI();
            if (!requestURI.equals("/login")
                    && !requestURI.equals("/register")
                    && !requestURI.startsWith("/js/")
                    && !requestURI.startsWith("/css/")
                    && !requestURI.startsWith("/images/")) {
                response.sendRedirect("/login");
                return;
            }
        }

        if (token != null && jwtService.validateToken(token)) {
            String username = jwtService.getUsernameFromToken(token);
            CustomerDetails customerDetails = (CustomerDetails) customerDetailsService.loadUserByUsername(username);
            Authentication authentication = jwtService.getAuthentication(token, customerDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
