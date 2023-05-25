package dodam.b1nd.dgit.domain.github.presentation.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubTopDto {
    private String githubId;
    private String name;
    private int winCount;
    private String userImage;
    private String bio;
}
