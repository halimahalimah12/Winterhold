package com.indocyber.Winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDeleteItemDto {
    private final String name;
}
