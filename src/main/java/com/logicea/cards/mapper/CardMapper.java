package com.logicea.cards.mapper;

import com.logicea.cards.dto.CardDto;
import com.logicea.cards.models.Card;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CardMapper {
    public static Set<CardDto> toCardDtos(List<Card> cards) {
        Set<CardDto> countyDto = new HashSet<>();
        cards.forEach(card -> countyDto.add(CardDto.toCardDto(card)));
        return countyDto;
    }

    public static CardDto toCardDto(Card cardSaved) {
        return CardDto.toCardDto(cardSaved);
    }

    public static Card toCardDto(CardDto cardDto) {
        return CardDto.toCard(cardDto);
    }
}
