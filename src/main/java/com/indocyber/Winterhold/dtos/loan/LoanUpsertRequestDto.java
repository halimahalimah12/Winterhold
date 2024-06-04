package com.indocyber.Winterhold.dtos.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoanUpsertRequestDto {

    private Integer id;
    @NotBlank (message = "Customer tidak boleh kosong!")
    @NotNull
    private String customerId;
    @NotBlank (message = "Book tidak boleh kosong!")
    @NotNull
    private String bookId;
    @NotNull (message = "Loan Date tidak boleh kosong!")
    private LocalDate loanDate;
    private String note;

}
