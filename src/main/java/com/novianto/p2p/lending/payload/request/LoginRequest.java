package com.novianto.p2p.lending.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class LoginRequest {

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    public @NotBlank String getNickname() {
        return nickname;
    }

    public void setNickname(@NotBlank String nickname) {
        this.nickname = nickname;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}