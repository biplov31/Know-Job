package com.example.KnowJob.service;

import com.example.KnowJob.dto.VerificationOtpDto;
import com.example.KnowJob.model.VerificationOtp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final Long OTP_VALIDITY = TimeUnit.MINUTES.toSeconds(60);

    private final RedisTemplate<String, String> redisTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public Optional<VerificationOtp> get(String key) {
        try {
            String jsonStr = redisTemplate.opsForValue().get(key);
            if (jsonStr == null) return Optional.empty();

            return Optional.of(objectMapper.readValue(jsonStr, VerificationOtp.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void set(String key, VerificationOtp otp) {
        try {
            String jsonValue = objectMapper.writeValueAsString(otp);
            redisTemplate.opsForValue().set(key, jsonValue, OTP_VALIDITY, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public boolean isOtpValid(VerificationOtpDto verificationOtpDto) {
        // if we use userId as the key, there is possible duplication when a user tries to verify more than one emails at the same time
        // email is a safer choice to use as key

        Optional<VerificationOtp> optionalVerificationOtp = get(verificationOtpDto.getEmail());

        if (optionalVerificationOtp.isEmpty()) {
            return false;
        }

        VerificationOtp verificationOtp = optionalVerificationOtp.get();
        String actualCode = verificationOtp.getOtpCode();

        return actualCode.equals(verificationOtpDto.getOtpCode()) && isOtpNonExpired(verificationOtp);
    }

    public Boolean isOtpNonExpired(VerificationOtp verificationOtp) {
        return LocalDateTime.now().isBefore(verificationOtp.getCreatedAt().plusMinutes(OTP_VALIDITY));
    }

}
