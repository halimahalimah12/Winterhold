package com.indocyber.Winterhold.restAPI.auth;

import com.indocyber.Winterhold.dtos.auth.AuthTokenRequestDto;
import com.indocyber.Winterhold.dtos.auth.AuthTokenResponseDto;
import org.springframework.stereotype.Service;


public interface JwtAuthService {
    AuthTokenResponseDto creatToken(AuthTokenRequestDto dto);
}
