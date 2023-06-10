package com.logicea.cards.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CardFilter {
    private String name;
    private String color;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String date;
    private String status;
    private Integer page;
    private Integer size;

}
