package com.logicea.cards.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CardRes<T> {

    private T data;
    private String message;
    private Integer status;
}
