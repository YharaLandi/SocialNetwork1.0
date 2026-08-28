package com.SocialNetwork.SocialNetwork.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String nomeCompleto;
    private String email;
    private String password;
}
