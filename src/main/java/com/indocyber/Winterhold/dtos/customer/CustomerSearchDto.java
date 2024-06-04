package com.indocyber.Winterhold.dtos.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSearchDto {
    private String membershipNumber;
    private String name;
    private Integer pageNumber;
    private Integer pageSize;
}
