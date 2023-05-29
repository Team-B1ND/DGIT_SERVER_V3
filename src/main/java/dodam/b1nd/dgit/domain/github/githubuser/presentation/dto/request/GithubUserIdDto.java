package dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.request;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubUserIdDto {
    private String githubId;
}
