package com.logicea.cards.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class CardFilter {
    private String name;
    private String color;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime date;
    private String status;
    private Integer page;
    private Integer size;

}
