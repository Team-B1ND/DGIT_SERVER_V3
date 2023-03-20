package dodam.b1nd.dgit.domain.githubuser.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubUserDto {
    private String githubId;
}
