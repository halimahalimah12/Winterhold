package com.indocyber.Winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDeleteItemDto {
    private final String membershipNumber;
    private  final String fullName;
}
