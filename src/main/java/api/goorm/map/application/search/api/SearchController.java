package api.goorm.map.application.search.api;

import api.goorm.map.application.search.dto.SearchRequestDto;
import api.goorm.map.application.search.dto.SearchResponseDto;
import api.goorm.map.application.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "검색")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "검색 기록 저장", description = "사용자가 검색한 장소를 저장합니다.")
    @PostMapping("/location")
    public ResponseEntity<SearchResponseDto> updateSearch(@RequestBody SearchRequestDto dto) {
        SearchResponseDto searchResponseDto = searchService.save(dto);
        return ResponseEntity.ok(searchResponseDto);
    }
}
