package com.logicea.cards.api;

import com.logicea.cards.payload.request.CardFilter;
import com.logicea.cards.payload.request.CardReq;
import com.logicea.cards.payload.response.CardRes;
import com.logicea.cards.payload.response.JwtResponse;
import com.logicea.cards.security.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
@Tag(name = "CardController", description = "Card management APIs")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @Operation(
            summary = "Retrieve a card by ID",
            description = "Get a card by card id",
            tags = {"card", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    ResponseEntity<?> getCardById(@PathVariable("id") Long id) {
        return cardService.getCard(id);
    }

    @GetMapping("filter")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @Operation(
            summary = "Retrieve cards with a filter",
            description = "Get cards by using the card filter criteria",
            tags = {"filter", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    ResponseEntity<?> filteredCards(@Valid @RequestBody CardFilter cardFilter) {
        return cardService.getCards(cardFilter);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all cards by by user",
            description = "fetch all user cards from the database",
            tags = {"fetch", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> userCards() {
        return cardService.getCards();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a card by id",
            description = "Use a cards id to delete it from the database",
            tags = {"delete card", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    ResponseEntity<?> deleteCard(@PathVariable("id") Long id) {
        return cardService.deleteCard(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @Operation(
            summary = "Create a card for a user",
            description = "Endpoint to create a new card",
            tags = {"create card", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    ResponseEntity<?> userCreateCard(@Valid @RequestBody CardReq cardReq) {
        return cardService.createCard(cardReq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    @Operation(
            summary = "Update a card by id",
            description = "Endpoint to update card details",
            tags = {"update card details", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    ResponseEntity<?> updateCard(@PathVariable("id") Long id,
                                 @Valid @RequestBody CardReq cardReq) {
        return cardService.updateCard(id, cardReq);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Admin endpoint to retrieve all cards",
            description = "Admin to get all cards",
            tags = {"admin get all cards", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CardRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public ResponseEntity<?> adminAccessCards() {
        return ResponseEntity.ok(cardService.adminCards());
    }
}
