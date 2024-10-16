package api.goorm.map.application.recommend.api;

import api.goorm.map.application.recommend.dto.RecommendRequestDto;
import api.goorm.map.application.recommend.dto.RecommendResponseDto;
import api.goorm.map.application.recommend.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
@Tag(name = "Recommend", description = "추천")
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "장소 추천", description = "사용자의 현재 장소를 기반으로 검색 기록이 높은 장소를 추천합니다.")
    @PostMapping("/location")
    public ResponseEntity<RecommendResponseDto> getRecommend(@RequestBody RecommendRequestDto dto) {
        RecommendResponseDto recommendResponseDto = recommendService.getRecommend(dto);
        return ResponseEntity.ok(recommendResponseDto);
    }
}
