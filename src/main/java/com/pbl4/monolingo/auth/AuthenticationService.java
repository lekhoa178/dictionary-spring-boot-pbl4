package com.pbl4.monolingo.auth;


import com.pbl4.monolingo.dao.AccountRepository;
import com.pbl4.monolingo.dao.AccountTypeRepository;
import com.pbl4.monolingo.dao.ExtraInformationRepository;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.entity.AccountType;
import com.pbl4.monolingo.entity.DataPerDay;
import com.pbl4.monolingo.entity.ExtraInformation;
import com.pbl4.monolingo.security.JwtService;
import com.pbl4.monolingo.service.DataPerDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class    AuthenticationService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountTypeRepository accountTypeRepository;
    private final ExtraInformationRepository extraInformationRepository;
    private final DataPerDayService dataPerDayService;
    private final Map<Integer, LocalDateTime> loginTimes = new HashMap<>();

    public AuthenticationResponse register(RegisterRequest request) {
        AccountType accountType = accountTypeRepository.findAccountTypeByType("ROLE_LEARNER").orElseThrow();
        var user = new Account(request.getUsername(),passwordEncoder.encode(request.getPassword()),request.getEmail(),true,accountType);
        ExtraInformation newExtra = new ExtraInformation();
        newExtra.setBalance(0);
        newExtra.setHearts(5);
        newExtra.setNumberOfLoginDay(0);
        newExtra.setNumberOfConsecutiveDay(0);
        newExtra.setAccount(user);
        user.setName(request.getUsername());
        user.setGender(true);
        repository.save(user);
        extraInformationRepository.save(newExtra);
        dataPerDayService.updateAccountDPD(user.getAccountId(),0,0);
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
        user.setOnline(true);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public Map<Integer, LocalDateTime> getLoginTimes() {
        return loginTimes;
    }
}
