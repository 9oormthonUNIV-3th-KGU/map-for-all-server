package api.goorm.map.application.search.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SearchRequestDto {

    private String location;
    private Double latitude;
    private Double longitude;
}
