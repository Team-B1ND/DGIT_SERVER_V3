package dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddGithubRepositoryDto {

    @NotNull
    private String githubId;
    @NotNull
    private String repositoryName;
}