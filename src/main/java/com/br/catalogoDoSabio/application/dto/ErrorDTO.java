package com.br.catalogoDoSabio.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ErrorDTO {

    private String code;
    private String title;
    private String description;
    private String traceId;
}
