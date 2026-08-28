package com.SocialNetwork.SocialNetwork.service;

import com.SocialNetwork.SocialNetwork.exception.DuplicateLikeException;
import com.SocialNetwork.SocialNetwork.exception.ResourceNotFoundException;
import com.SocialNetwork.SocialNetwork.model.Like;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public Like addLike(Utente utente, Post post) {
        if (likeRepository.findByUtenteAndPost(utente, post).isPresent()) {
            throw new DuplicateLikeException("Hai già messo like a questo post");
        }
        Like like = Like.builder()
                .utente(utente)
                .post(post)
                .build();
        return likeRepository.save(like);
    }

    public void removeLike(Utente utente, Post post) {
        Like like = likeRepository.findByUtenteAndPost(utente, post)
                .orElseThrow(() -> new ResourceNotFoundException("Like non trovato"));
        likeRepository.delete(like);
    }

    public List<Like> findByPost(Post post) {
        return likeRepository.findByPost(post);
    }
}
