package com.novianto.p2p.lending.service.auth;

import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.repository.UserRepository;
import com.novianto.p2p.lending.security.CustomUserDetails;
import com.novianto.p2p.lending.service.global.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LogService logService;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByNickname(nickname)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with nickname" + nickname));
            logService.log("INFO", "CustomUserDetailsService", "Pengguna diautentikasi: " + nickname, Thread.currentThread().getName());
            return new CustomUserDetails(user);
        } catch (UsernameNotFoundException e) {
            logService.log("WARN", "CustomUserDetailsService", "Gagal menemukan pengguna: " + nickname, Thread.currentThread().getName(), e.toString());
            throw e;
        }
    }
}
