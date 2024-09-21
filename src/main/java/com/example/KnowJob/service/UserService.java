package com.example.KnowJob.service;

import com.example.KnowJob.config.AuthenticationProviderImpl;
import com.example.KnowJob.dto.UserLoginRequestDto;
import com.example.KnowJob.dto.UserSignUpRequestDto;
import com.example.KnowJob.dto.UserResponseDto;
import com.example.KnowJob.mapper.UserMapper;
import com.example.KnowJob.model.User;
import com.example.KnowJob.model.UserDetailsImpl;
import com.example.KnowJob.repository.UserRepository;
import com.example.KnowJob.util.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoggedInUser loggedInUser;

    public UserResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        User user = userMapper.map(userSignUpRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        if (savedUser != null && savedUser.getId() != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var jwt = jwtService.generateToken(new UserDetailsImpl(savedUser));
            return UserResponseDto.builder().accessToken(jwt).build();
        } else {
            throw new RuntimeException("Failed to create user.");
        }
    }

    public UserResponseDto logIn(UserLoginRequestDto userLoginRequestDto) {
        boolean authenticated = authenticateUser(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());

        if (authenticated) {
            UserDetails userDetails = loggedInUser.getLoggedInUser();

            var jwt = jwtService.generateToken(userDetails);
            return UserResponseDto.builder().accessToken(jwt).build();
        } else {
            throw new RuntimeException("Failed to log in.");
        }
    }

    public boolean authenticateUser(String email, String password) {
        AuthenticationProviderImpl authenticationProvider = new AuthenticationProviderImpl(userDetailsService, passwordEncoder);
        Authentication authToken = new UsernamePasswordAuthenticationToken(email, password);
        // authenticationProvider uses UserDetailsService.loadByUsername() to fetch the user from the database and then verifies password for the given email
        // once the user is authenticated, the SecurityContext holds the authentication information including the UserDetails object
        Authentication authentication = authenticationProvider.authenticate(authToken);

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }
        return false;
    }



}
