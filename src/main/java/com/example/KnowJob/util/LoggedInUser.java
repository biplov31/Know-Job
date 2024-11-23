package com.example.KnowJob.util;

import com.example.KnowJob.model.User;
import com.example.KnowJob.model.UserDetailsImpl;
import com.example.KnowJob.repository.UserRepository;
import com.example.KnowJob.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUser {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    public UserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // in some cases, the principal may just be the username (a String), depending on how the authentication is performed (e.g. when using a token-based system or custom authentication mechanisms), in this case we need to fetch the full UserDetails object
            // when using traditional form-based authentication or custom implementations, the principal might contain a UserDetails object
            // UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String loggedInEmail = authentication.getName();
            return userDetailsService.loadUserByUsername(loggedInEmail);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        // String loggedInEmail = authentication.getName();
        //
        // return userRepository.findByEmail(loggedInEmail)
        //         .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public User getLoggedInUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UsernameNotFoundException("User not found.");
        }

        String loggedInEmail = authentication.getName();
        return userRepository.findByEmail(loggedInEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

}
