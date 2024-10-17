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
        String locationName = dto.getLocation();
        Location location;
        if(locationRepository.findByName(locationName).isEmpty()) {
            // 새로운 장소 검색 시
            location = Location.builder()
                    .views(1)
                    .name(locationName)
                    .latitude(dto.getLatitude())
                    .longitude(dto.getLongitude())
                    .build();
            locationRepository.save(location);
        } else {
            // 기존 장소 검색 시
            location = locationRepository.findByName(locationName).get();
            // 검색수 증가
            location.incrementViews();
        }

        if(userService.findByKakaoId(userService.getCurrentUserId()) != null) {
            // 현재 로그인한 사용자
            User user = userService.findByKakaoId(userService.getCurrentUserId());
            Search search = Search.builder()
                    .location(location)
                    .user(user)
                    .build();
            searchRepository.save(search);
        }
        return SearchResponseDto.toSearchResponseDto(location);
    }
}
