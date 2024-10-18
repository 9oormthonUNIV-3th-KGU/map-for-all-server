package api.goorm.map.application.user.service;

import api.goorm.map.application.user.dao.UserRepository;
import api.goorm.map.application.user.dto.UserResponseDto;
import api.goorm.map.application.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(String kakaoId, String nickname, String profileImage, String email) {

        Optional<User> existingUser = userRepository.findByKakaoId(kakaoId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNickname(nickname);
            user.setProfileImage(profileImage);
            user.setEmail(email);
            return userRepository.save(user).getId();
        }

        User newUser = User.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .build();
        return userRepository.save(newUser).getId();
    }

    @Transactional
    public Long updateUsername(String username) {
        UserResponseDto userDto = getCurrentLoginUser();
        User user = findByKakaoId(userDto.getKakaoId());
        user.setNickname(username);
        return user.getId();
    }

    public User findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId).orElse(null);
    }

    public UserResponseDto getCurrentLoginUser() {
        String kakaoId = getCurrentUserId();
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return UserResponseDto.toDto(user);
    }

    public String getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User) {
            return String.valueOf(((OAuth2User) principal).getAttributes().get("id"));
        } else {
            return principal.toString();
        }
    }
}
