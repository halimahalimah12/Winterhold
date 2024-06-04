package com.indocyber.Winterhold.restAPI.controller;

import com.indocyber.Winterhold.dtos.auth.AuthTokenRequestDto;
import com.indocyber.Winterhold.dtos.auth.AuthTokenResponseDto;
import com.indocyber.Winterhold.restAPI.auth.JwtAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
public class RestAuthController {
    private final JwtAuthService service;

    public RestAuthController(JwtAuthService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<AuthTokenResponseDto> createToken(@RequestBody AuthTokenRequestDto dto) {
        return ResponseEntity.ok(service.creatToken(dto));
    }

}
