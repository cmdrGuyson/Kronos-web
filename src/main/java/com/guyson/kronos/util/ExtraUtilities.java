package com.guyson.kronos.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

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

}
