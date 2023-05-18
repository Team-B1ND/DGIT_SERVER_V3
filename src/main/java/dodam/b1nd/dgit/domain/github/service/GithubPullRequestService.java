package dodam.b1nd.dgit.domain.github.service;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.presentation.dto.request.GithubPullRequestDto;
import dodam.b1nd.dgit.domain.github.repository.GithubUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubPullRequestService {

    private final GithubUserRepository githubUserRepository;

    public List<GithubPullRequestDto> getPullRequestListSort() {
        List<GithubUser> githubUserList = githubUserRepository.findAll(Sort.by(Sort.Direction.DESC, "pullRequest"));

        List<GithubPullRequestDto> result = githubUserList.stream()
                .map(githubUser ->
                        GithubPullRequestDto.builder()
                                .githubId(githubUser.getGithubId())
                                .name(githubUser.getUser().getName())
                                .pullRequest(githubUser.getPullRequest())
                                .userImage(githubUser.getUserImage())
                                .bio(githubUser.getBio()).build()
                ).collect(Collectors.toList());

        return result;
    }
}
