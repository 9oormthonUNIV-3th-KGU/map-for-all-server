package api.goorm.map.application.user.dao;

import api.goorm.map.application.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByKakaoId(String email);

    Optional<User> findByKakaoIdAndActiveFalse(String kakaoId);
}
