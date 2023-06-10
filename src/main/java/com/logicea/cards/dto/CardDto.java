package com.logicea.cards.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicea.cards.models.Card;
import com.logicea.cards.models.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDto {
    private Long id;
    private String color;
    private String name;
    private Status status;
    private String description;

    public static CardDto toCardDto(Card card) {
        return new CardDto().setStatus(card.getStatus()).setId(card.getId()).setName(card.getName()).setColor(card.getColor()).setDescription(card.getDescription());
    }

    public static Card toCard(CardDto card) {
        return Card.builder()
                .color(card.getColor())
                .name(card.getName())
                .status(card.getStatus())
                .description(card.getDescription())
                .build();
    }


}
