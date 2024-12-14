package com.novianto.p2p.lending.service.auth.impl;

import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.model.enumType.Role;
import com.novianto.p2p.lending.payload.request.auth.RegisterRequest;
import com.novianto.p2p.lending.repository.UserRepository;
import com.novianto.p2p.lending.service.auth.UserService;
import com.novianto.p2p.lending.service.global.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LogService logService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LogService logService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.logService = logService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByNickname(registerRequest.getNickname())) {
            logService.log("WARN", "UserService", "Mencoba mendaftar dengan nama panggilan yang ada: " + registerRequest.getNickname(), Thread.currentThread().getName(), "NicknameAlreadyTaken");
            throw new RuntimeException("Nickname telah digunakan!");
        }

        User user = User.builder()
                .nickname(registerRequest.getNickname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();

        Set<Role> roles = new HashSet<>();
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            roles.add(Role.ROLE_BORROWER);
        } else {
            for (String roleStr : registerRequest.getRoles()) {
                try {
                    Role role = Role.valueOf(roleStr.toUpperCase());
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    logService.log("ERROR", "UserService", "Role tidak diketahui: " + roleStr, Thread.currentThread().getName(), e.toString());
                    throw new RuntimeException("Role " + roleStr + " tidak ditemukan.");
                }
            }
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        logService.log("INFO", "UserService", "Pengguna baru terdaftar: " + savedUser.getNickname(), Thread.currentThread().getName());
        return savedUser;
    }
}
