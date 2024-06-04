package com.indocyber.Winterhold.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDto {
    private final String token;
}
