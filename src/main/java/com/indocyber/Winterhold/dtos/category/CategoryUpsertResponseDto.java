package com.indocyber.Winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpsertResponseDto {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;

}
