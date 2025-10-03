package com.example.financedashboardpro.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class PublicTokenRequest {
    @NotBlank
    private String publicToken;
}
