package api.goorm.map.application.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class RecommendRequestDto {
    private Double latitude;
    private Double longitude;
}
