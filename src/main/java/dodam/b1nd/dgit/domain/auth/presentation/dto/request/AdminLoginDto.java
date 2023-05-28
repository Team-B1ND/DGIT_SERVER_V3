package dodam.b1nd.dgit.domain.auth.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLoginDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String pw;
}
