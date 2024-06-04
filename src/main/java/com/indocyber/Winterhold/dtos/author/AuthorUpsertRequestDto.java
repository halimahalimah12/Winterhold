package com.indocyber.Winterhold.dtos.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class AuthorUpsertRequestDto {
    private Integer id;
    @NotBlank (message = "Title tidak boleh kosong!")
    @NotNull
    private String title;
    @NotBlank (message = "First Name tidak boleh kosong!")
    @NotNull
    private String firstName;
    private String lastName;
    @Past
    @NotNull (message = "Birth Date tidak boleh kosong!")
    private LocalDate birthDate;
    @Past
    private LocalDate deceaseDate;
    private String education;
    private String summary;
}
