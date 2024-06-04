package com.indocyber.Winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AuthorRowItemDto {
    private Integer id;
    private String name;
    private Integer age;
    private String status;
    private String education;
}
