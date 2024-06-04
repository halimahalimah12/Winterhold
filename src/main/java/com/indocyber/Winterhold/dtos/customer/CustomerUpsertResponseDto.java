package com.indocyber.Winterhold.dtos.customer;

import com.indocyber.Winterhold.models.Gender;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CustomerUpsertResponseDto {
    private String membershipNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String phone;
    private String address;
    private LocalDate membershipExpireDate;
}
