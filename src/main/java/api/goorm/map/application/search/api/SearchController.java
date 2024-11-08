package api.goorm.map.application.search.api;

import api.goorm.map.application.search.dto.SearchRequestDto;
import api.goorm.map.application.search.dto.SearchResponseDto;
import api.goorm.map.application.search.service.SearchService;
import api.goorm.map.application.user.entity.User;
import api.goorm.map.application.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "검색")
public class SearchController {

    private final SearchService searchService;
    private final UserService userService;

    @Operation(summary = "검색 기록 저장", description = "사용자가 검색한 장소를 저장합니다.")
    @PostMapping("/location")
    public ResponseEntity<SearchResponseDto> updateSearch(@RequestBody SearchRequestDto dto) {
        User user = null;
        try {
            user = userService.getCurrentUser();
        } catch (RuntimeException ignored){}

        SearchResponseDto searchResponseDto = searchService.save(dto, user);
        return ResponseEntity.ok(searchResponseDto);
    }

    @Operation(summary = "검색 기록 조회", description = "최근 5건이 조회됩니다.")
    @PostMapping("/get")
    public ResponseEntity<List<SearchResponseDto>> getSearch() {
        List<SearchResponseDto> searches = searchService.getSearchList(userService.getCurrentUser());
        return ResponseEntity.ok(searches);
    }

    @Operation(summary = "검색 기록 삭제", description = "검색 기록을 삭제합니다.")
    @PostMapping("/delete")
    public ResponseEntity<Long> deleteSearch(@RequestParam Long locationId) {
        Long id = searchService.deleteSearchByUserIdAndLocationId(userService.getCurrentUser().getId(), locationId);
        return ResponseEntity.ok(id);
    }
}
