package com.SocialNetwork.SocialNetwork.service;

import com.SocialNetwork.SocialNetwork.model.Commento;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.repository.CommentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentoService {

    private final CommentoRepository commentoRepository;

    public Commento save(Commento commento) {
        return commentoRepository.save(commento);
    }

    public List<Commento> findAll() {
        return commentoRepository.findAll();
    }

    public Commento findById(Long id) {
        return commentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commento non trovato con id: " + id));
    }

    public List<Commento> findByPost(Post post) {
        return commentoRepository.findByPost(post);
    }
}
