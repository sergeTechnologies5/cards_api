package com.logicea.cards.security.services.impl;

import com.logicea.cards.mapper.CardMapper;
import com.logicea.cards.models.Card;
import com.logicea.cards.models.ERole;
import com.logicea.cards.models.Role;
import com.logicea.cards.models.Status;
import com.logicea.cards.payload.request.CardFilter;
import com.logicea.cards.payload.request.CardReq;
import com.logicea.cards.payload.response.CardRes;
import com.logicea.cards.repository.CardRepository;
import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.security.services.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Override
    public ResponseEntity<CardRes> createCard(CardReq cardReq) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
        var optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        var card = Card.builder().name(cardReq.getName()).status(Status.TODO).description(cardReq.getDescription()).color(cardReq.getColor()).build();
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            card.setUsers(Set.of(user));
            user.setCards(Set.of(card));
            var cardSaved = cardRepository.save(card);

            return ResponseEntity.ok(CardRes.builder().message("Card created successfully").status(0).data(CardMapper.toCardDto(cardSaved)).build());
        }
        return ResponseEntity.ok(CardRes.builder().message("Card creation failed").status(0).data(null).build());

    }

    @Override
    public ResponseEntity<?> getCards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
        var optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            if (user.getRoles().contains(Role.builder().name(ERole.ROLE_MEMBER).build())) {
                return ResponseEntity.ok(CardRes.builder().message("Cards retrieved successfully").status(0).data(CardMapper.toCardDtos(user.getCards().stream().toList())).build());
            }
            var cards = cardRepository.findAll();
            return ResponseEntity.ok(CardRes.builder().message("Cards retrieved successfully").status(0).data(CardMapper.toCardDtos(cards)).build());
        }
        return ResponseEntity.ok(CardRes.builder().message("No user cards").status(0).build());

    }

    @Override
    public ResponseEntity<?> deleteCard(Long id) {
        cardRepository.deleteById(id);
        return ResponseEntity.ok(CardRes.builder().message("Card deleted successfully").status(0).data(null).build());
    }

    @Override
    public ResponseEntity<?> updateCard(Long id, CardReq cardReq) {
        var optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            var card = optionalCard.get();
            card.setName(cardReq.getName());
            card.setColor(cardReq.getColor());
            card.setDescription(cardReq.getDescription());
            card.setStatus(card.getStatus());
            var card_ = cardRepository.save(card);
            return ResponseEntity.ok(CardRes.builder().message("Card updated successfully").status(0).data(card_).build());
        }
        return ResponseEntity.ok(CardRes.builder().message("Card not Found for Update").status(0).data(null).build());
    }

    @Override
    public ResponseEntity<?> getCard(Long id) {
        var optionalCard = cardRepository.findById(id);

        return optionalCard.map(card -> ResponseEntity.ok(CardRes.builder().message("Card updated successfully").status(0).data(card).build())).orElseGet(() -> ResponseEntity.ok(CardRes.builder().message("Card not Found").status(0).data(null).build()));
    }

    @Override
    public ResponseEntity<?> getCards(CardFilter cardFilter) {
        var sort = Sort.by(Sort.Direction.DESC, "color").and(Sort.by(Sort.Direction.ASC, "status")).and(Sort.by(Sort.Direction.ASC, "createdOn"));
        Pageable paging = PageRequest.of(cardFilter.getPage(), cardFilter.getSize(), sort);

        ZonedDateTime dateTime = convertDate(cardFilter.getDate());

        var pageCards = cardRepository.findByColorContainingAndNameContainingAndStatusAndCreatedOn(cardFilter.getColor(), cardFilter.getName(), Status.valueOf(cardFilter.getStatus()),dateTime, paging);
        Map<String, Object> response = new HashMap<>();
        var cards = pageCards.getContent();
        response.put("cards", CardMapper.toCardDtos(cards));
        response.put("currentPage", pageCards.getNumber());
        response.put("totalItems", pageCards.getTotalElements());
        response.put("totalPages", pageCards.getTotalPages());
        return ResponseEntity.ok(CardRes.builder().message("Card not Found for Update").status(0).data(response).build());
    }

    private ZonedDateTime convertDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date_ = LocalDate.parse(date, formatter);
        ZonedDateTime result = date_.atStartOfDay(ZoneId.systemDefault());
        return result;
    }

    @Override
    public ResponseEntity<?> adminCards() {
        var cards = cardRepository.findAll();
        return ResponseEntity.ok(CardRes.builder().message("Cards retrieved successfully").status(0).data(CardMapper.toCardDtos(cards)).build());
    }
}
