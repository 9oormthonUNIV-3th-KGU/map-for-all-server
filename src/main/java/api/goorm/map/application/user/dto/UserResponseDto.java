package api.goorm.map.application.user.dto;

import api.goorm.map.application.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private String kakaoId;
    private String username;
    private String email;

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .kakaoId(user.getKakaoId())
                .username(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
