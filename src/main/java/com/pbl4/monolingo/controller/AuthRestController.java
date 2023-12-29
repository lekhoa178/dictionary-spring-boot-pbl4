package com.pbl4.monolingo.controller;

import com.pbl4.monolingo.auth.AuthenticationRequest;
import com.pbl4.monolingo.auth.AuthenticationResponse;
import com.pbl4.monolingo.auth.AuthenticationService;
import com.pbl4.monolingo.auth.RegisterRequest;
import com.pbl4.monolingo.entity.Account;
import com.pbl4.monolingo.security.JwtService;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.utility.dto.Message;
import com.pbl4.monolingo.utility.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationService service;
    private final AccountService accountService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/updateAccount")
    public ResponseEntity<ResponseMessage> updateAccount(@RequestBody Account account){
        accountService.updateAccount(account);
        return ResponseEntity.ok(new ResponseMessage("Update success!!!"));
    }

    @PostMapping("/checkTokenExpired")
    public  ResponseEntity<ResponseMessage> checkTokenExpired (@RequestBody AuthenticationResponse token) {
        boolean rs = jwtService.isTokenExpired(token.getToken());
        System.out.println("RS: "+ rs);
        return ResponseEntity.ok(new ResponseMessage("false"));
    }
}
