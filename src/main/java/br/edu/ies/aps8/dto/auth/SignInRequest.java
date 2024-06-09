package br.edu.ies.aps8.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRequest {
    private String username;
    private String password;
}
