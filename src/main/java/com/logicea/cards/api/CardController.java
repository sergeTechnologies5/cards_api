package com.logicea.cards.api;

import com.logicea.cards.payload.request.CardFilter;
import com.logicea.cards.payload.request.CardReq;
import com.logicea.cards.security.services.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> userCard(@PathVariable("id") Long id) {
        return cardService.getCard(id);
    }

    @GetMapping("filter")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> filteredCards(@Valid @RequestBody CardFilter cardFilter) {
        return cardService.getCards(cardFilter);
    }

    @GetMapping
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> userCards() {
        return cardService.getCards();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> deleteCard(@PathVariable("id") Long id) {
        return cardService.deleteCard(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> userCreateCard(@Valid @RequestBody CardReq cardReq) {
        return cardService.createCard(cardReq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> updateCard(@PathVariable("id") Long id,
                                 @Valid @RequestBody CardReq cardReq) {
        return cardService.updateCard(id, cardReq);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccessCards() {
        return ResponseEntity.ok(cardService.adminCards());
    }
}
