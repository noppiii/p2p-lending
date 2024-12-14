package com.novianto.p2p.lending.service.auth;

import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.payload.request.auth.RegisterRequest;

public interface UserService {

    User registerUser(RegisterRequest registerRequest);
}
