package com.example.KnowJob.config;

import com.example.KnowJob.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .formLogin(c -> c.disable())
                .httpBasic(c -> c.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        c -> c.requestMatchers("/review").hasRole("REVIEWER")
                                .requestMatchers(HttpMethod.GET, "/review").permitAll()
                                .requestMatchers("/comment").hasRole("READER")
                                .requestMatchers(HttpMethod.GET, "/comment").permitAll()
                                .requestMatchers("/post").hasRole("READER")
                                .requestMatchers(HttpMethod.GET, "/post").permitAll()
                                .requestMatchers("/vote").hasRole("READER")
                                .requestMatchers("/company").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/company").permitAll()
                                .requestMatchers("/user/signup").permitAll()
                                .requestMatchers("/user/login").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsServiceImpl)
                .authenticationProvider(new AuthenticationProviderImpl(userDetailsServiceImpl, passwordEncoder))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
