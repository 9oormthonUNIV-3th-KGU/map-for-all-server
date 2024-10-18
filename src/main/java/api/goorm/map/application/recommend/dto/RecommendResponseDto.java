package api.goorm.map.application.recommend.dto;

import api.goorm.map.application.search.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
public class RecommendResponseDto {
    private List<Location> recommendLocations;

    public static RecommendResponseDto toRecommendResponseDto(List<Location> recommendLocations) {
        return RecommendResponseDto.builder()
                .recommendLocations(recommendLocations)
                .build();
    }
}