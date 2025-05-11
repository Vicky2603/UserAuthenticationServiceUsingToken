package com.scaler.userauthenticationserviceusingtokens.Repository;

import com.scaler.userauthenticationserviceusingtokens.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailEquals(String email);
}
