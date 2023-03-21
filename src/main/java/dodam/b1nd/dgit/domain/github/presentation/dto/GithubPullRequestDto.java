package dodam.b1nd.dgit.domain.github.presentation.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubPullRequestDto {
    private String githubId;
    private int pullRequest;
    private String userImage;
    private String bio;
}
