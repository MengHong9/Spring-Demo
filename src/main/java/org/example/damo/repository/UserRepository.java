package org.example.damo.repository;

import org.example.damo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User , Long> {
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
