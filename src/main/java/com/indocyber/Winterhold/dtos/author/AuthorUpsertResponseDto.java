package com.indocyber.Winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AuthorUpsertResponseDto {
    private Integer id;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deceaseDate;
    private String education;
    private String summary;
}
