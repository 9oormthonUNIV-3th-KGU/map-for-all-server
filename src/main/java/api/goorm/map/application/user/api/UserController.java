package api.goorm.map.application.user.api;

import api.goorm.map.application.user.dto.UserResponseDto;
import api.goorm.map.application.user.service.UserService;
import api.goorm.map.common.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 내 정보를 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<UserResponseDto> getUser() {
        UserResponseDto userResponseDto = userService.getCurrentLoginUser();
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "내 이름 수정", description = "이름 수정이 가능합니다.")
    @GetMapping("/update")
    public ApiResponse<Long> updateUsername(
            @RequestParam(name = "username") String username
    ) {
        Long id = userService.updateUsername(username);
        return ApiResponse.createSuccess(id);
    }
}
