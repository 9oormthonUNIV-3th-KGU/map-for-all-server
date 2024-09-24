package api.goorm.map.common.auth.api;

import api.goorm.map.common.auth.service.RefreshTokenService;
import api.goorm.map.common.config.UrlProperties;
import api.goorm.map.common.auth.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UrlProperties urlProperties;

    @Operation(description = "액세스 토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshTokenFromCookies(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Refresh Token provided");
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired Refresh Token");
        }

        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        String savedRefreshToken = refreshTokenService.getRefreshToken(userId);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token mismatch");
        }

        String newAccessToken = jwtTokenProvider.createToken(userId);
        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return ResponseEntity.ok("Access Token reissued successfully");
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
