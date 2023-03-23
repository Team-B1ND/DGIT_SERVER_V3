package dodam.b1nd.dgit.domain.github.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRepositoryDto {

    private String repositoryName;
    private int totalStars;
    private String githubId;
    private String githubUserImage;

}