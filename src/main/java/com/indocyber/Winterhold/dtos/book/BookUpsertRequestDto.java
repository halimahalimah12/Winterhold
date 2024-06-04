package com.indocyber.Winterhold.dtos.book;

import com.indocyber.Winterhold.models.Author;
import com.indocyber.Winterhold.models.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookUpsertRequestDto {
    @NotBlank(message = "Code tidak boleh kosong!")
    @NotNull
    private String code;
    @NotBlank (message = "Title Book tidak boleh kosong!")
    @NotNull
    private String title;
    private String categoryId;
    private Integer authorId;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
    private Boolean isBorrowed;
}
