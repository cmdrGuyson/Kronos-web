package com.guyson.kronos.service;

import com.guyson.kronos.domain.User;
import com.guyson.kronos.dto.AuthResponse;
import com.guyson.kronos.dto.ChangePasswordRequest;
import com.guyson.kronos.dto.LoginRequest;
import com.guyson.kronos.dto.SimpleMessageDto;
import com.guyson.kronos.exception.KronosException;
import com.guyson.kronos.repository.UserRepository;
import com.guyson.kronos.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse login(LoginRequest request) throws KronosException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        // Find user from system or return error
        User user = userRepository.findById(request.getUsername()).orElseThrow(()->new KronosException("User not found!"));

        return new AuthResponse(token, request.getUsername(), user.getRole());
    }

    @Transactional
    public SimpleMessageDto changePassword(ChangePasswordRequest request) throws KronosException {

        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.guyson.kronos.domain.User found_user = userRepository.findById(user.getUsername()).orElseThrow(()->new KronosException("User not found"));


        //Check if old password matches
        if (!passwordEncoder.matches(request.getOldPassword(), found_user.getPassword())) {
            throw new KronosException("Old password is incorrect");
        }

        //Change password
        found_user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        //Save in database
        userRepository.save(found_user);

        return new SimpleMessageDto("Password changed successfully", HttpStatus.OK);

    }

}
