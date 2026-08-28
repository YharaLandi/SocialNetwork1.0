package com.SocialNetwork.SocialNetwork.service;

import com.SocialNetwork.SocialNetwork.exception.ResourceNotFoundException;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Role;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.repository.CommentoRepository;
import com.SocialNetwork.SocialNetwork.repository.LikeRepository;
import com.SocialNetwork.SocialNetwork.repository.PostRepository;
import com.SocialNetwork.SocialNetwork.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteService implements UserDetailsService {

    private final UtenteRepository utenteRepository;
    private final PostRepository postRepository;
    private final CommentoRepository commentoRepository;
    private final LikeRepository likeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Utente findById(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato: " + username));
    }

    public Utente changeRuolo(Long id, Role nuovoRuolo) {
        Utente utente = findById(id);
        utente.setRuolo(nuovoRuolo);
        return utenteRepository.save(utente);
    }

    public void delete(Long id) {
        Utente utente = findById(id);

        likeRepository.deleteAll(likeRepository.findByUtente(utente));
        commentoRepository.deleteAll(commentoRepository.findByAutore(utente));

        List<Post> posts = postRepository.findByAutore(utente);
        posts.forEach(post -> {
            likeRepository.deleteAll(likeRepository.findByPost(post));
            commentoRepository.deleteAll(commentoRepository.findByPost(post));
        });

        postRepository.deleteAll(posts);
        utenteRepository.delete(utente);
    }
}
