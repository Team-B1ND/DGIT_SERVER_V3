package dodam.b1nd.dgit.domain.auth.presentation.dto.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenApiDto {
    private String message;
    private UserInfoDto data;
}
