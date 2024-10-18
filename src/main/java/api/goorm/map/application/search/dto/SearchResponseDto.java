package api.goorm.map.application.search.dto;

import api.goorm.map.application.search.entity.Location;
import lombok.*;

@Getter
@Builder
public class SearchResponseDto {
    private String locationName;
    private Double latitude;
    private Double longitude;

    public static SearchResponseDto toSearchResponseDto(Location location) {
        return SearchResponseDto.builder()
                .locationName(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
