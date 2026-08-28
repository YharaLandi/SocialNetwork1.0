package com.SocialNetwork.SocialNetwork.runner;

import com.SocialNetwork.SocialNetwork.dto.RegisterRequest;
import com.SocialNetwork.SocialNetwork.model.Post;
import com.SocialNetwork.SocialNetwork.model.Role;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.service.AuthService;
import com.SocialNetwork.SocialNetwork.service.LikeService;
import com.SocialNetwork.SocialNetwork.service.PostService;
import com.SocialNetwork.SocialNetwork.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;
    private final UtenteService utenteService;
    private final PostService postService;
    private final LikeService likeService;

    @Override
    public void run(String... args) throws Exception {

        RegisterRequest marioReq = new RegisterRequest();
        marioReq.setUsername("mario_rossi");
        marioReq.setNomeCompleto("Mario Rossi");
        marioReq.setEmail("mario@example.com");
        marioReq.setPassword("password123");
        Utente mario = authService.register(marioReq);
        mario = utenteService.changeRuolo(mario.getId(), Role.MODERATOR);

        RegisterRequest giuliaReq = new RegisterRequest();
        giuliaReq.setUsername("giulia_bianchi");
        giuliaReq.setNomeCompleto("Giulia Bianchi");
        giuliaReq.setEmail("giulia@example.com");
        giuliaReq.setPassword("password123");
        Utente giulia = authService.register(giuliaReq);

        RegisterRequest lucaReq = new RegisterRequest();
        lucaReq.setUsername("luca_verdi");
        lucaReq.setNomeCompleto("Luca Verdi");
        lucaReq.setEmail("luca@example.com");
        lucaReq.setPassword("password123");
        Utente luca = authService.register(lucaReq);

        System.out.println("--- Utenti creati ---");
        utenteService.findAll().forEach(u ->
                System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getRuolo()));

        Post post1 = postService.save(Post.builder()
                .testo("Primo post di Mario!")
                .dataPubblicazione(LocalDateTime.now())
                .autore(mario)
                .build());

        Post post2 = postService.save(Post.builder()
                .testo("Giulia condivide i suoi pensieri.")
                .dataPubblicazione(LocalDateTime.now())
                .autore(giulia)
                .build());

        System.out.println("\n--- Post creati ---");
        postService.findAll().forEach(p ->
                System.out.println(p.getId() + " | " + p.getAutore().getUsername() + ": " + p.getTesto()));

        likeService.addLike(giulia, post1);
        likeService.addLike(luca, post1);
        likeService.addLike(mario, post2);

        System.out.println("\n--- Like sul post1 ---");
        likeService.findByPost(post1).forEach(l ->
                System.out.println("Like di: " + l.getUtente().getUsername()));
    }
}
