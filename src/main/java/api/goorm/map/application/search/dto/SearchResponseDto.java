package api.goorm.map.application.search.dto;

import api.goorm.map.application.search.entity.Location;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchResponseDto {
    private String location;
    private Double latitude;
    private Double longitude;

    public static SearchResponseDto toSearchResponseDto(Location location) {
        return SearchResponseDto.builder()
                .location(location.getLocation())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
