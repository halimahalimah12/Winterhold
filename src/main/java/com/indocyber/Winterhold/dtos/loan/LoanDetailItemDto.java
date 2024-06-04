package com.indocyber.Winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanDetailItemDto {
    private final String titleBook;
    private final String category;
    private final String nameAuthor;
    private final Integer floor;
    private final String isle;
    private final String bay;
    private final String membershipNumber;
    private final String nameCustomer;
    private final String phone;
    private final LocalDate membershipExpireDate;

}
