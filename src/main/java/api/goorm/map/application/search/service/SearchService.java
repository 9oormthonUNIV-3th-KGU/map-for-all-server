package api.goorm.map.application.search.service;

import api.goorm.map.application.search.dao.LocationRepository;
import api.goorm.map.application.search.dao.SearchRepository;
import api.goorm.map.application.search.dto.SearchRequestDto;
import api.goorm.map.application.search.dto.SearchResponseDto;
import api.goorm.map.application.search.entity.Location;
import api.goorm.map.application.search.entity.Search;
import api.goorm.map.application.user.entity.User;
import api.goorm.map.application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserService userService;
    private final LocationRepository locationRepository;
    private final SearchRepository searchRepository;

    @Transactional
    public SearchResponseDto save(SearchRequestDto dto) {
        String locationName = dto.getLocationName();
        Location location = locationRepository.findByName(locationName)
                .orElseGet(()-> {
                    // 새로운 장소 검색 시 location 생성
                    Location newLocation = Location.builder()
                            .views(1)
                            .name(locationName)
                            .latitude(dto.getLatitude())
                            .longitude(dto.getLongitude())
                            .build();
                    locationRepository.save(newLocation);
                    return newLocation;
                });

        // 기존 장소일 경우 조회수 증가
        if(location.getViews() > 1) {
            location.incrementViews();
        }

        User user = userService.findByKakaoId(userService.getCurrentUserId());
        if(user != null) {
            // 로그인한 사용자가 있을 시 검색 조회 기록 저장
            Search search = Search.builder()
                    .location(location)
                    .user(user)
                    .build();
            searchRepository.save(search);
        }
        return SearchResponseDto.toSearchResponseDto(location);
    }
}
