package dodam.b1nd.dgit.domain.auth.presentation.dto.api;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DAuthToken {
    private String code;
    private String client_id;
    private String client_secret;
}
