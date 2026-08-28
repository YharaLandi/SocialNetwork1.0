package com.SocialNetwork.SocialNetwork.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String testo;

    @Column(nullable = false)
    private LocalDateTime dataPubblicazione;

    @ManyToOne
    @JoinColumn(name = "autore_id", nullable = false)
    private Utente autore;
}
