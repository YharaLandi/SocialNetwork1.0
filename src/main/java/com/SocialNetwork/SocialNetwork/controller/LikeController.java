package com.SocialNetwork.SocialNetwork.controller;

import com.SocialNetwork.SocialNetwork.model.Like;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.service.LikeService;
import com.SocialNetwork.SocialNetwork.service.PostService;
import com.SocialNetwork.SocialNetwork.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;
    private final UtenteService utenteService;

    @PostMapping
    public ResponseEntity<Like> addLike(@PathVariable Long postId,
                                        @AuthenticationPrincipal UserDetails principal) {
        Utente utente = utenteService.findByUsername(principal.getUsername());
        Post post = postService.findById(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.addLike(utente, post));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetails principal) {
        Utente utente = utenteService.findByUsername(principal.getUsername());
        Post post = postService.findById(postId);
        likeService.removeLike(utente, post);
        return ResponseEntity.noContent().build();
    }
}
