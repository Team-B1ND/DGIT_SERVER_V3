package dodam.b1nd.dgit.domain.github.presentation.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubRankDto {
    private String githubId;
    private int contributions;
    private String userImage;
    private String bio;
}
