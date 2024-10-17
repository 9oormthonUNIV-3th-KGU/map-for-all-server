package api.goorm.map.application.recommend.service;

import api.goorm.map.application.recommend.dto.RecommendRequestDto;
import api.goorm.map.application.recommend.dto.RecommendResponseDto;
import api.goorm.map.application.search.dao.LocationRepository;
import api.goorm.map.application.search.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final LocationRepository locationRepository;

    @Transactional
    public RecommendResponseDto getRecommend(RecommendRequestDto dto) {
        // 현재 위치의 위도 경도
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        // 1km 범위 내의 장소 조회
        List<Location> locations = locationRepository.findLocationsWithinRadius(latitude, longitude);

        // 조회된 장소들을 추천수 기준으로 정렬, 상위 3개 선택
        List<Location> topLocations = locations.stream()
                .sorted(Comparator.comparing(Location::getViews).reversed())
                .limit(3)
                .collect(Collectors.toList());

        return RecommendResponseDto.toRecommendResponseDto(topLocations);
    }
}
