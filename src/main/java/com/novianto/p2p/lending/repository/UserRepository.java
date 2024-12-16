package com.novianto.p2p.lending.repository;

import com.novianto.p2p.lending.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Boolean existsByNickname(String nickname);
}
