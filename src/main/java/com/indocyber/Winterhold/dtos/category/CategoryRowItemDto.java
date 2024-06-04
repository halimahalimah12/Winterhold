package com.indocyber.Winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRowItemDto {
    private String name;
    private Integer floor;
    private String isla;
    private String bay;
    private Integer totalBook;
}
