package com.indocyber.Winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookSearchDto {
    private String title;
    private String author;
    private Boolean isBorrowed;
    private Integer pageNumber;
    private Integer pageSize;
}
