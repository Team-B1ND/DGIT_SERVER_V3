package dodam.b1nd.dgit.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private final String access;
    private final String refresh;
    private final int access_expire;
    private final int refresh_expire;

}