package com.novianto.p2p.lending.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;
}