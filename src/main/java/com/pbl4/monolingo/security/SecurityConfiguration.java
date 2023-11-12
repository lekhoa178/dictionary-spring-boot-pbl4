package com.pbl4.monolingo.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Autowired

    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/img/**").permitAll()
                .requestMatchers("/javascript/**").permitAll()
                .requestMatchers("/vendor/**").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/api/auth/**")
                .permitAll()
                .requestMatchers("/public/**")
                .permitAll()
                .requestMatchers("/template/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin(formLogin -> formLogin.loginPage("/login").permitAll());
//                .addFilterBefore((Filter) new JwtTokenInterceptor(),UsernamePasswordAuthenticationFilter.class);
//                http.csrf().disable();

        return http.build();
    }
}
