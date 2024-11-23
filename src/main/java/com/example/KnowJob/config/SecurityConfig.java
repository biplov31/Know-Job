package com.example.KnowJob.config;

import com.example.KnowJob.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
                // If the application requires requests to be authenticated (but provides no valid mechanism for authenticating them such as form-based authentication, basic authentication or JWT verification), any attempts to access secured routes will
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        // Spring Security processes rules in the order they are defined, and once a match is found, the subsequent rules are not evaluated.
                        // We want to ensure that more specific rules (like allowing only GET requests) come before more general ones
                        c -> c.requestMatchers(HttpMethod.GET, "/review").permitAll()
                                .requestMatchers(HttpMethod.GET, "/review/**").permitAll()
                                .requestMatchers("/review/**").hasAnyRole("REVIEWER", "READER")
                                .requestMatchers("/review").hasRole("REVIEWER")
                                .requestMatchers(HttpMethod.GET, "/comment").permitAll()
                                .requestMatchers(HttpMethod.GET, "/comment/**").permitAll()
                                .requestMatchers("/comment").hasAnyRole("REVIEWER", "READER")
                                .requestMatchers("/comment/**").hasAnyRole("REVIEWER", "READER")
                                .requestMatchers("/comment/review/**").hasAnyRole("REVIEWER", "READER")
                                .requestMatchers(HttpMethod.GET, "/post").permitAll()
                                .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                                .requestMatchers("/post").hasAnyRole("REVIEWER", "READER")
                                .requestMatchers("/vote").hasRole("READER")
                                .requestMatchers(HttpMethod.GET, "/company").permitAll()
                                .requestMatchers(HttpMethod.GET, "/company/**").permitAll()
                                .requestMatchers("/company").hasRole("ADMIN")
                                .requestMatchers("/user/signup").permitAll()
                                .requestMatchers("/user/login").permitAll()
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsServiceImpl)
                .authenticationProvider(new AuthenticationProviderImpl(userDetailsServiceImpl, passwordEncoder));
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
