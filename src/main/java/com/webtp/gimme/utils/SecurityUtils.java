package com.webtp.gimme.utils;

import com.webtp.gimme.security.CustomerDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;

public class SecurityUtils {

    public static void checkAuthorization(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails customerDetails = (CustomerDetails) authentication.getPrincipal();
        if (!customerDetails.getCustomer().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'avez pas les droits nécessaires.");
        }
    }

    public static void setCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("JWT", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
