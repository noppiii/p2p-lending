package com.novianto.p2p.lending.service.auth;

import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.payload.request.auth.RegisterRequest;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {

    User registerUser(RegisterRequest registerRequest);

    Optional<User> findByNickname(String nickname);

    void updateUserBalance(User user, BigDecimal amount);
}
