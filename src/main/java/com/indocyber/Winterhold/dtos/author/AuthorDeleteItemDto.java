package com.indocyber.Winterhold.dtos.author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDeleteItemDto {
    private final Integer id;
    private final String fullName;
}
