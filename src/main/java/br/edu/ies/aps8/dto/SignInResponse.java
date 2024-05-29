package br.edu.ies.aps8.dto;

import br.edu.ies.aps8.model.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SignInResponse {
    private List<Role> roles;
    private String token;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresIn;
}
