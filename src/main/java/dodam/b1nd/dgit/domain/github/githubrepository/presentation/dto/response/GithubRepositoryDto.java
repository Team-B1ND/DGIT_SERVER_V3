package dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRepositoryDto {
    private long repositoryId;
    private String repositoryName;
    private int totalStars;
    private String githubId;
    private String githubUserImage;

}