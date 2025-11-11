package com.feirinha.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer quantity;
}