package api.goorm.map.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UrlProperties {

    @Value("${url.redirectUrl.onBoard}")
    private String onBoardUrl;

    @Value("${url.redirectUrl.main}")
    private String mainUrl;

    @Value("${url.domain}")
    private String domain;
}
