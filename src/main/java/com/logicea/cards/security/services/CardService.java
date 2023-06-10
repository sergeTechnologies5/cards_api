package com.logicea.cards.security.services;

import com.logicea.cards.payload.request.CardFilter;
import com.logicea.cards.payload.request.CardReq;
import com.logicea.cards.payload.response.CardRes;
import org.springframework.http.ResponseEntity;

public interface CardService {
    ResponseEntity<CardRes> createCard(CardReq cardReq);

    ResponseEntity<?> getCards();

    ResponseEntity<?> deleteCard(Long id);

    ResponseEntity<?> updateCard(Long id,CardReq cardReq);

    ResponseEntity<?> getCard(Long id);

    ResponseEntity<?> getCards(CardFilter cardFilter);

    ResponseEntity<?>  adminCards();
}
