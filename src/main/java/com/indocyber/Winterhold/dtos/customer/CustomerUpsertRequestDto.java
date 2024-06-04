package com.indocyber.Winterhold.dtos.customer;

import com.indocyber.Winterhold.models.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerUpsertRequestDto {
    @NotBlank(message = "Membership Number tidak boleh kosong!")
    @NotNull
    private String membershipNumber;
    @NotBlank (message = "First Name tidak boleh kosong!")
    @NotNull
    private String firstName;
    private String lastName;
    @Past
    @NotNull (message = "Birth Date tidak boleh kosong!")
    private LocalDate birthDate;
    @NotBlank (message = "Gender tidak boleh kosong!")
    private String gender;
    private String phone;
    private String address;
    private LocalDate membershipExpireDate;

}
