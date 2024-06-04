package com.indocyber.Winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanUpsertResponseDto {
    private Integer id;
    private String customerId;
    private String bookId;
    private LocalDate loanDate;
    private String note;

}
