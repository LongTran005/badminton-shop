package com.badmintonshop.repository;

import com.badmintonshop.entity.Cart;
import com.badmintonshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

    Optional<Cart> findBySessionId(String sessionId);

    Optional<Cart> findByUser_UserId(Long userId);

    void deleteBySessionId(String sessionId);
}
