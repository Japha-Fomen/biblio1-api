package com.example.biblio1_api.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime date,
        int status,
        String message
) {
}
