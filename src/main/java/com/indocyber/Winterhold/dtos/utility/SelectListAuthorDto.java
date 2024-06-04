package com.indocyber.Winterhold.dtos.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectListAuthorDto {
    private String text;
    private Integer value;
}
