package com.indocyber.Winterhold.dtos.utility;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectListBookDto {
    private String text;
    private String value;
}
