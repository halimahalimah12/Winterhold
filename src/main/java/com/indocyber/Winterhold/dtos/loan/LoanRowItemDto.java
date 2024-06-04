package com.indocyber.Winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class LoanRowItemDto {
    private Integer id;
    private String titleBook;
    private String customerName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String isLate;
}
