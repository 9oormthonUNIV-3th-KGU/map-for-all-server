package api.goorm.map.common.oauth2.handler;

import api.goorm.map.common.auth.service.RefreshTokenService;
import api.goorm.map.common.config.UrlProperties;
import api.goorm.map.common.auth.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UrlProperties urlProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String kakaoId = oAuth2User.getAttribute("id").toString();
        boolean isFirstLogin = Boolean.TRUE.equals(oAuth2User.getAttribute("isFirstLogin"));

        String accessToken = jwtTokenProvider.createToken(kakaoId);
        String refreshToken = refreshTokenService.getRefreshToken(kakaoId);

        if (refreshToken == null || jwtTokenProvider.isTokenExpired(refreshToken)) {
            refreshToken = jwtTokenProvider.createRefreshToken(kakaoId);
            refreshTokenService.saveRefreshToken(kakaoId, refreshToken, jwtTokenProvider.getRefreshValidityInMilliseconds());
        }

        addCookie(response, "AccessToken", accessToken, jwtTokenProvider.getValidityInMilliseconds());
        addCookie(response, "RefreshToken", refreshToken, jwtTokenProvider.getRefreshValidityInMilliseconds());

        String redirectUrl = isFirstLogin ? urlProperties.getOnBoardUrl() : urlProperties.getMainUrl();

        response.sendRedirect(redirectUrl);
    }

    private void addCookie(HttpServletResponse response, String name, String value, long maxAgeMillis) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .maxAge(maxAgeMillis / 1000)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .sameSite("None")
                .domain(urlProperties.getDomain())
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
