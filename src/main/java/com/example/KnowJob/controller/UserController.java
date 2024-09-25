package com.example.KnowJob.controller;

import com.example.KnowJob.dto.UserLoginRequestDto;
import com.example.KnowJob.dto.UserSignUpRequestDto;
import com.example.KnowJob.dto.UserResponseDto;
import com.example.KnowJob.dto.VerificationOtpDto;
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

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> logIn(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserResponseDto userResponseDto = userService.logIn(userLoginRequestDto);

        if (userResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> getOtp() {
        userService.sendOtpEmail();

        return ResponseEntity.status(HttpStatus.OK).body("Verify your account with the OTP sent to your email.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerificationOtpDto verificationOtpDto) {
        boolean isVerified = userService.verifyEmail(verificationOtpDto);

        if (isVerified) {
            return ResponseEntity.status(HttpStatus.OK).body("Verification successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed.");
        }
    }


}
