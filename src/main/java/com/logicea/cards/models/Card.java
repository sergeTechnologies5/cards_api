package com.logicea.cards.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "cards")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private String color;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private ZonedDateTime createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @ManyToMany(mappedBy = "cards")
    private Set<User> users;
    @PrePersist
    final protected void onCreate() {
        createdOn = ZonedDateTime.now();
        updatedOn = ZonedDateTime.now();
    }

    @PreUpdate
    final protected void onUpdate() {
        updatedOn = ZonedDateTime.now();
    }
}
