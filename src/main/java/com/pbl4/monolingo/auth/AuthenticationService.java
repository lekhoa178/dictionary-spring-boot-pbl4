package com.pbl4.monolingo.auth;


import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.AccountTypeRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.AccountType;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.security.JwtService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public static Account account;
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountTypeRepository accountTypeRepository;

    public AuthenticationResponse register(RegisterRequest request) {
//        var user = Account.builder().username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
        AccountType accountType = accountTypeRepository.findAccountTypeByType("ROLE_LEARNER").orElseThrow();
        var user = new Account(request.getUsername(),passwordEncoder.encode(request.getPassword()),accountType);
//        ExtraInformation newExtra = user;
        repository .save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        account = user;
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
