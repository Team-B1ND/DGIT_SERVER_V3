package dodam.b1nd.dgit.global.enums.baseurl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DodamBaseUrl {
    DODAM_AUTH("http://dauth.b1nd.com/api"),
    DODAM_OPENAPI("http://open.dodam.b1nd.com/api");

    private String endpoint;
}
