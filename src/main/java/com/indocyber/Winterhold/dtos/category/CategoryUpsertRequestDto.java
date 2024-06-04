package com.indocyber.Winterhold.dtos.category;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpsertRequestDto {
    @NotBlank (message = "Name Book tidak boleh kosong!")
    @NotNull
    private String name;
    @NotNull (message = "Floor Book tidak boleh kosong!")
    private Integer floor;
    @NotBlank (message = "Isle Book tidak boleh kosong!")
    @NotNull
    private String isle;
    @NotBlank (message = "Bay Book tidak boleh kosong!")
    @NotNull
    private String bay;

}
