package com.SocialNetwork.SocialNetwork.controller;

import com.SocialNetwork.SocialNetwork.dto.ChangeRoleRequest;
import com.SocialNetwork.SocialNetwork.model.Utente;
import com.SocialNetwork.SocialNetwork.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utenti")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    @PatchMapping("/{id}/ruolo")
    public ResponseEntity<Utente> changeRuolo(@PathVariable Long id,
                                               @RequestBody ChangeRoleRequest request) {
        return ResponseEntity.ok(utenteService.changeRuolo(id, request.getRuolo()));
    }
}
