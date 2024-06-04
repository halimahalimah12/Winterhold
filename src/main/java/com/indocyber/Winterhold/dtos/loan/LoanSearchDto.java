package com.indocyber.Winterhold.dtos.loan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoanSearchDto {
    private String titleBook;
    private String customerName;
    private Integer pageNumber;
    private Integer pageSize;

}
