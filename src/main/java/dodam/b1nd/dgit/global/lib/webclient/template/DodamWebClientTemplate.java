package dodam.b1nd.dgit.global.lib.webclient.template;

import dodam.b1nd.dgit.domain.auth.presentation.dto.api.DAuthToken;
import dodam.b1nd.dgit.domain.auth.presentation.dto.api.OpenApiDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.api.TokenRo;
import dodam.b1nd.dgit.global.lib.webclient.WebClientUtil;
import dodam.b1nd.dgit.global.lib.webclient.parser.HeaderParser;
import dodam.b1nd.dgit.global.properties.OAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dodam.b1nd.dgit.global.enums.baseurl.DodamBaseUrl.DODAM_AUTH;
import static dodam.b1nd.dgit.global.enums.baseurl.DodamBaseUrl.DODAM_OPENAPI;

@Component
@RequiredArgsConstructor
public class DodamWebClientTemplate {

    private final WebClientUtil webClientUtil;
    private final OAuthProperties authProperties;

    public String auth(String code, String path) {
        return webClientUtil.post(
                DODAM_AUTH.getEndpoint() + path,
                DAuthToken.builder()
                        .code(code)
                        .client_id(authProperties.getClientId())
                        .client_secret(authProperties.getClientSecret()).build(),
                TokenRo.class
        ).getBody().getAccessToken();
    }

    public OpenApiDto openApi(String accessToken, String path) {
        return webClientUtil.get(
                DODAM_OPENAPI.getEndpoint() + path,
                HeaderParser.builder()
                        .type("Authorization")
                        .value("Bearer " + accessToken).build(),
                OpenApiDto.class
        ).getBody();
    }
}
