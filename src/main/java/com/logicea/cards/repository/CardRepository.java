package com.logicea.cards.repository;

import com.logicea.cards.models.Card;
import com.logicea.cards.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, PagingAndSortingRepository<Card, Long> {
    Page<Card> findByColorContainingAndNameContainingAndStatusAndCreatedOn(String color, String name, Status status, ZonedDateTime createdOn, Pageable pageable);
}
