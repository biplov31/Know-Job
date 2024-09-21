package com.example.KnowJob.mapper;

import com.example.KnowJob.dto.UserSignUpRequestDto;
import com.example.KnowJob.dto.UserResponseDto;
import com.example.KnowJob.model.User;
import com.example.KnowJob.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserSignUpRequestDto userSignUpRequestDto) {
        return User.builder()
                .username(userSignUpRequestDto.getUsername())
                .email(userSignUpRequestDto.getEmail())
                .password(userSignUpRequestDto.getPassword())
                .role(UserRole.valueOf("ROLE_" + userSignUpRequestDto.getRole()))
                .build();
    }

}
