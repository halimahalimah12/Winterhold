package com.indocyber.Winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerRowItemDto {
    private String membershipNumber;
    private String fullName;
    private LocalDate expireDate;
}
