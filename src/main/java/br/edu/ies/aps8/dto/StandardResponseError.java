package br.edu.ies.aps8.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponseError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
