package dodam.b1nd.dgit.domain.auth.presentation.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReProvideToken {

    private String accessToken;

}
