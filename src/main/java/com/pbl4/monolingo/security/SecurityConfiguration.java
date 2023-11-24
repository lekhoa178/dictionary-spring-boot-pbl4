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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin(formLogin -> formLogin.loginPage("/public/login").permitAll());

        http.logout(logout -> logout
                .logoutUrl("public/logout") // URL to trigger logout (e.g. POST /logout)
                .logoutSuccessHandler(logoutSuccessHandler()) // Your custom logout success handler if required
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID") // If cookies are used, adjust accordingly for JWT
                .permitAll()

        );

//                .addFilterBefore((Filter) new JwtTokenInterceptor(),UsernamePasswordAuthenticationFilter.class);
//                http.csrf().disable();

        return http.build();
    }
    public LogoutSuccessHandler logoutSuccessHandler() {

        return (request, response, authentication) -> {

            // Custom logic after successful logout, e.g. redirect to login page

            response.sendRedirect("/public/login");

        };

    }
}
