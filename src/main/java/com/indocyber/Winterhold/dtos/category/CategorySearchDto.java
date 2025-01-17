package com.indocyber.Winterhold.dtos.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorySearchDto {
    private String name;
    private Integer pageNumber;
    private Integer pageSize;
}
