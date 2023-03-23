package dodam.b1nd.dgit.domain.github.facade;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.domain.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRepositoryDto;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubUserFacade {

    private final GithubUserRepository githubUserRepository;

    public GithubUser findByGithubId(String githubId) {
        return githubUserRepository.findById(githubId)
                .orElseThrow(() -> CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND));
    }

    public void existsByGithubId(String githubId) {
        if(!githubUserRepository.existsById(githubId)) {
            throw new CustomError(ErrorCode.GITHUB_USER_NOT_FOUND);
        }
    }

    public GithubRepositoryDto toDto(GithubRepository githubRepository) {
        GithubUser githubUser = githubRepository.getGithubUser();

        return GithubRepositoryDto.builder()
                .repositoryName(githubRepository.getRepositoryName())
                .totalStars(githubRepository.getTotalStars())
                .githubId(githubUser.getGithubId())
                .githubUserImage(githubUser.getUserImage())
                .build();
    }

}