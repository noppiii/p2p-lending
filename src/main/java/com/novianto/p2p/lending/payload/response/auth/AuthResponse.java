package com.novianto.p2p.lending.payload.response.auth;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {

    @NonNull
    private String accessToken;
    private String tokenType = "Bearer";
}
