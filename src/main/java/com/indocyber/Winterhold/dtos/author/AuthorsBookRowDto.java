package com.indocyber.Winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AuthorsBookRowDto {
    private String title;
    private String Category;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;

}
