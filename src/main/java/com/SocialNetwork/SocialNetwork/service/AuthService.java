package com.SocialNetwork.SocialNetwork.service;

import com.SocialNetwork.SocialNetwork.dto.LoginRequest;
import com.SocialNetwork.SocialNetwork.dto.LoginResponse;
import com.SocialNetwork.SocialNetwork.dto.RegisterRequest;
import com.SocialNetwork.SocialNetwork.model.Role;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.repository.UtenteRepository;
import com.SocialNetwork.SocialNetwork.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public Utente register(RegisterRequest request) {
        if (utenteRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username già in uso");
        }
        if (utenteRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email già in uso");
        }

        Utente utente = Utente.builder()
                .username(request.getUsername())
                .nomeCompleto(request.getNomeCompleto())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .ruolo(Role.MEMBER)
                .build();

        return utenteRepository.save(utente);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new LoginResponse(token);
    }
}
