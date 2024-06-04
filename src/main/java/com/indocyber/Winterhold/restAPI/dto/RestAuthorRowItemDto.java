package com.indocyber.Winterhold.restAPI.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RestAuthorRowItemDto {
    private Integer id;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
