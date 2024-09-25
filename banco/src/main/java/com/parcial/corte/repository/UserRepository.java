package com.parcial.corte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parcial.corte.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNumeroCuenta(String numeroCuenta);
    Optional<User> findByUsername(String username);
    Optional<User> getUserByNumeroCuenta(String numeroCuenta);
}