package dodam.b1nd.dgit.domain.user.presentation.dto;

import dodam.b1nd.dgit.domain.user.domain.enums.Role;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String email;
    private String name;
    private String githubId;
    private String userImage;
    private Role role;
}
