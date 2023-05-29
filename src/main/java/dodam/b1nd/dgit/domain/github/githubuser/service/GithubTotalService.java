package dodam.b1nd.dgit.domain.github.githubuser.service;

import dodam.b1nd.dgit.domain.github.githubuser.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubRankDto;
import dodam.b1nd.dgit.domain.github.githubuser.repository.GithubUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubTotalService {

    private final GithubUserRepository githubUserRepository;

    public List<GithubRankDto> getTotalListSort() {
        List<GithubUser> githubUserList = githubUserRepository.findAllByAuthStatusOrderByTotalContributionsDesc(
                AuthStatus.ALLOWED
        );

        List<GithubRankDto> result = githubUserList.stream()
                .map(githubUser ->
                        GithubRankDto.builder()
                                .githubId(githubUser.getGithubId())
                                .name(githubUser.getUser().getName())
                                .contributions(githubUser.getTotalContributions())
                                .userImage(githubUser.getUserImage())
                                .bio(githubUser.getBio()).build()
                ).collect(Collectors.toList());

        return result;
    }
}
