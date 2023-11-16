//package com.pbl4.monolingo.security;
//
//import jakarta.servlet.Filter;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new MD5PasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        // define query to retrieve user by username
//        userDetailsManager
//                .setUsersByUsernameQuery(
//                        "select username, password, enabled " +
//                                "from account " +
//                                "where username=?"
//                );
//
//        // define query to retrieve authorities/roles by username
//        userDetailsManager
//                .setAuthoritiesByUsernameQuery(
//                        "select username, account_type.role " +
//                                "from account join account_type on account.type_id = account_type.id " +
//                                "where username=?"
//                );
//
//        return userDetailsManager;
//    }
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(configurer ->
//                configurer
////                        .requestMatchers(HttpMethod.GET, "/learn").permitAll()
//                        .requestMatchers("/css/**").permitAll()
//                        .requestMatchers("/img/**").permitAll()
//                        .requestMatchers("/javascript/**").permitAll()
//                        .anyRequest().authenticated()
//        );
//
//        //http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
////        http
////                .authenticationProvider(authenticationProvider)
////                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        // use HTTP Basic authentication
//        http.httpBasic();
//        http.formLogin();
////        http.formLogin(formLogin -> formLogin
////                /*.loginPage("/login")*/.permitAll());
//
//        // disable Cross-Site Request Forgery (CSRF)
//        // in general, not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
//        http.csrf().disable();
//
//        return http.build();
//
//    }
//
//
//}
