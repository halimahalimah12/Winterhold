package com.indocyber.Winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookUpsertResponseDto {
    private String code;
    private String title;
    private String categoryId;
    private String authorId;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
}
