package api.goorm.map.application.user.service;

import api.goorm.map.application.user.dao.UserRepository;
import api.goorm.map.application.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(String kakaoId, String nickname, String profileImage) {

        Optional<User> existingUser = userRepository.findByKakaoId(kakaoId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
            return userRepository.save(user).getId();
        }

        User newUser = User.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();

        return userRepository.save(newUser).getId();
    }

    public User findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElse(null);
    }
}
