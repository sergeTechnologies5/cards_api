package com.logicea.cards.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CardReq {
    @NotNull
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3,4}){1,2}$", message = "Color must conform to 6 alphanumeric character prefixed with a # format")
    private String color;
    private String description;
    private String status;
}
