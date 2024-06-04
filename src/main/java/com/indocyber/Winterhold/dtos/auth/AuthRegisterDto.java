package com.indocyber.Winterhold.dtos.auth;

import com.indocyber.Winterhold.validator.ComparePassword;
import com.indocyber.Winterhold.validator.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ComparePassword
public class AuthRegisterDto {
    @Username
    @NotNull
    @NotBlank (message = "Harus ada isinya untuk username!")
    public String username;
    @NotBlank
    @Size(min = 8, max=20)
    public String password;
    @NotBlank
    @Size(min = 8, max=20)
    public String confirmPassword;
}
