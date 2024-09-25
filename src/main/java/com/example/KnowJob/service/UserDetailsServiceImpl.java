package com.example.KnowJob.service;

import com.example.KnowJob.model.UserDetailsImpl;
import com.example.KnowJob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(username)
                .map((user) -> new UserDetailsImpl(user))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }



}
