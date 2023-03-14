package dodam.b1nd.dgit.global.config;

import dodam.b1nd.dgit.global.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class PropertiesConfig {
}