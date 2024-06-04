package com.indocyber.Winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookRowItemDto {
    private String code;
    private String title;
    private String author;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
}
