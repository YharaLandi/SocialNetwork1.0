package com.SocialNetwork.SocialNetwork.repository;

import com.SocialNetwork.SocialNetwork.model.Like;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPost(Post post);
    List<Like> findByUtente(Utente utente);
    Optional<Like> findByUtenteAndPost(Utente utente, Post post);
}
