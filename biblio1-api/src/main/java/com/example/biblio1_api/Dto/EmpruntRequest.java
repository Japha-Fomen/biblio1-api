package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotNull;

public record EmpruntRequest(
        @NotNull Long livreId,
        @NotNull Long membreId
) {
}
