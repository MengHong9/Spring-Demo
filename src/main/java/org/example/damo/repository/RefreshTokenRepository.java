package org.example.damo.repository;

import jakarta.transaction.Transactional;
import org.example.damo.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);


    @Modifying
    @Transactional
    @Query("delete from RefreshToken rt where rt.expiresAt < :now or rt.revoked = true ")
    void deleteExpiredAndRevokeTokens(@Param("now") LocalDateTime now);
}
