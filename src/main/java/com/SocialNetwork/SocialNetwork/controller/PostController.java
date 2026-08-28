package com.SocialNetwork.SocialNetwork.controller;

import com.SocialNetwork.SocialNetwork.dto.CreatePostRequest;
import com.SocialNetwork.SocialNetwork.dto.UpdatePostRequest;
import com.SocialNetwork.SocialNetwork.exception.UnauthorizedException;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.service.PostService;
import com.SocialNetwork.SocialNetwork.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UtenteService utenteService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> create(@RequestBody CreatePostRequest request,
                                       @AuthenticationPrincipal UserDetails principal) {
        Utente autore = utenteService.findByUsername(principal.getUsername());
        Post post = Post.builder()
                .testo(request.getTesto())
                .dataPubblicazione(LocalDateTime.now())
                .autore(autore)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(post));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> update(@PathVariable Long id,
                                       @RequestBody UpdatePostRequest request,
                                       @AuthenticationPrincipal UserDetails principal) {
        Post post = postService.findById(id);
        Utente utente = utenteService.findByUsername(principal.getUsername());
        if (!post.getAutore().getId().equals(utente.getId())) {
            throw new UnauthorizedException("Puoi modificare solo i tuoi post");
        }
        return ResponseEntity.ok(postService.update(id, request.getTesto()));
    }
}
