package dodam.b1nd.dgit.domain.github.service;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRankDto;
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
public class GithubTotalService {

    private final GithubUserRepository githubUserRepository;

    public List<GithubRankDto> getTotalListSort() {
        List<GithubUser> githubUserList = githubUserRepository.findAll(Sort.by(Sort.Direction.DESC, "totalContributions"));

        List<GithubRankDto> result = githubUserList.stream()
                .map(githubUser ->
                        GithubRankDto.builder()
                                .githubId(githubUser.getGithubId())
                                .contributions(githubUser.getTotalContributions())
                                .userImage(githubUser.getUserImage())
                                .bio(githubUser.getBio()).build()
                ).collect(Collectors.toList());

        return result;
    }
}