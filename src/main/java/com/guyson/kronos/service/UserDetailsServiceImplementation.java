package com.guyson.kronos.service;

import com.guyson.kronos.domain.User;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        //Find user from database
        Optional<User> userOptional = userRepository.findById(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("User not found"));

        //Return spring security user details object
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(),
                true, true, true, true,
                getAuthorities(user.getRole().toUpperCase()));
    }

    //Get user role
    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role));
    }
}
