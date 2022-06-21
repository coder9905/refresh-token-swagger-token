package uz.zako.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.entity.Admin;
import uz.zako.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findFirstByRefreshTokenOrderByCreatedAtDesc(String refreshToken);

}
