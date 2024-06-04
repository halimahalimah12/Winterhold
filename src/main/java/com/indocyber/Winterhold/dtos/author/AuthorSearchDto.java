package com.indocyber.Winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorSearchDto {
    private String name;
    private Integer pageNumber;
    private Integer pageSize;
}
