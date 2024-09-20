package com.example.KnowJob.service;

import com.example.KnowJob.dto.UserLoginRequestDto;
import com.example.KnowJob.dto.UserSignUpRequestDto;
import com.example.KnowJob.dto.UserResponseDto;
import com.example.KnowJob.mapper.UserMapper;
import com.example.KnowJob.model.User;
import com.example.KnowJob.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        User user = userMapper.map(userSignUpRequestDto);

        User savedUser = userRepository.save(user);
        if (savedUser.getId() != null) {
            return userMapper.map(savedUser);
        } else {
            throw new RuntimeException("Failed to save user.");
        }
    }



}
