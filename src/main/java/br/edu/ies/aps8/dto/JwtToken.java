package br.edu.ies.aps8.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    private String token;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresIn;

    public Duration expirationTime() {
        return Duration.between(issuedAt, expiresIn);
    }
}
