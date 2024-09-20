package com.example.KnowJob.controller;

import com.example.KnowJob.dto.UserSignUpRequestDto;
import com.example.KnowJob.dto.UserResponseDto;
import com.example.KnowJob.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        UserResponseDto userResponseDto = userService.signUp(userSignUpRequestDto);

        if (userResponseDto != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // .build() is used when there's no body
        }
    }

    // @GetMapping("/login")
    // public ResponseEntity<UserResponseDto> logIn(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
    //
    // }



}
