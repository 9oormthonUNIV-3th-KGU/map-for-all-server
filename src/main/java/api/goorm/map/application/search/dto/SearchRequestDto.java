package api.goorm.map.application.search.dto;

import lombok.*;

@Getter
@Builder
public class SearchRequestDto {
    private String locationName;
    private Double latitude;
    private Double longitude;
}
