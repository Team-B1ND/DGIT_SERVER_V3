package dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubPullRequestDto {
    private String githubId;
    private String name;
    private int pullRequest;
    private String userImage;
    private String bio;
}
