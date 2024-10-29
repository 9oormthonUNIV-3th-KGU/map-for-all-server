package api.goorm.map.application.search.dto;

import api.goorm.map.application.search.entity.Location;
import lombok.*;

@Getter
@Builder
public class SearchResponseDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    public static SearchResponseDto toSearchResponseDto(Location location) {
        return SearchResponseDto.builder()
                .id(location.getId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
