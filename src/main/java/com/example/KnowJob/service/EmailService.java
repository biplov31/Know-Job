package com.example.KnowJob.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String otp) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Email verification for KnowJob");
            mimeMessageHelper.setText("""
                    <div style="padding:24px; background-color:#eee; border-radius:24px; display:grid; place-items:center; gap:8px">
                        <h3 style="text-align:center; color:#008080; margin:12px; font-size:24px; font-weight:bolder">Welcome to KnowJob</h3>
                        <p style="text-align:center; color:#1e1e1e; font-size: 16px">Ready to share what you know?</p>
                        <p style="text-align:center; color:#000; font-size:16px">Start by verifying your email %s with this OTP: <span style="text-decoration:underline; font-weight:bold;">%s</span></p>
                    </div>
                    """.formatted(to, otp), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.warn("Messaging Exception: {}", e.getMessage());
        }
    }

}
