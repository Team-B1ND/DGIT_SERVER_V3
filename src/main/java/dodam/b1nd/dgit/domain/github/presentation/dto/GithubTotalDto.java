package dodam.b1nd.dgit.domain.github.presentation.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubTotalDto {
    private String githubId;
    private int totalContributions;
    private String userImage;
    private String bio;
}
