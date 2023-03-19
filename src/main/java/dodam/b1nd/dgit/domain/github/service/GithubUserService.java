package dodam.b1nd.dgit.domain.github.service;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.presentation.dto.GithubUserDto;
import dodam.b1nd.dgit.domain.github.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import github.query.GetContributionQuery;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubUserService {

    private final GithubUserRepository githubUserRepository;

    @Transactional
    public void save(User user, GithubUserDto githubUserDto) {
        return githubUserRepository.save(githubUserResponseToEntity(user, githubUser));
    }

    private GithubUser githubUserResponseToEntity(final User user, @NonNull GetContributionQuery.User githubUser) {
        return GithubUser.builder()
                .githubId(githubUser.login())
                .user(user)
                .totalContributions(githubUser.contributionsCollection().contributionCalendar().totalContributions())
                .userImage(githubUser.avatarUrl().toString())
                .bio(githubUser.bio())
                .build();
    }
}
