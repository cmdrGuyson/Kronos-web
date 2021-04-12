package com.guyson.kronos.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.regex.Pattern;

public class ExtraUtilities {

    //Check if user has given role ("ROLE_ADMIN" or "ROLE_STUDENT")
    public static boolean hasRole(String role) {
        boolean hasRole = false;
        try {
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            for (GrantedAuthority authority : authorities) {
                hasRole = authority.getAuthority().equals(role);
                System.out.println(authority.getAuthority());
                if (hasRole) {
                    break;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return hasRole;
    }

    //Get IP address of HTTP request source
    public static String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

    //Method to validate emails
    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

}
