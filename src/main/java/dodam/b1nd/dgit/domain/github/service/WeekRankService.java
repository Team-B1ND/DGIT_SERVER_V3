package dodam.b1nd.dgit.domain.github.service;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubWeek;
import dodam.b1nd.dgit.domain.github.domain.entity.WeekRank;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRankRecordDto;
import dodam.b1nd.dgit.domain.github.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.github.repository.WeekRankRepository;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeekRankService {

    private final WeekRankRepository weekRankRepository;
    private final GithubUserRepository githubUserRepository;

    public List<GithubRankRecordDto> getRankRecord(int page, int limit) {

        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        List<WeekRank> weekRankList = weekRankRepository.findAllByOrderByRankedDateDesc(pageRequest);

        return weekRankList.stream().map(weekRank ->
                GithubRankRecordDto.builder()
                        .rankedDate(weekRank.getRankedDate())
                        .githubId(weekRank.getGithubUser().getGithubId())
                        .name(weekRank.getGithubUser().getUser().getName())
                        .userImage(weekRank.getGithubUser().getUser().getName())
                        .bio(weekRank.getGithubUser().getBio())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public void saveRank(GithubWeek githubWeek) {

        GithubUser githubUser = this.getGithubUser(githubWeek.getGithubUser().getGithubId());

        weekRankRepository.save(
                WeekRank.builder()
                        .contribute(githubWeek.getContribute())
                        .githubUser(githubUser)
                        .build()
        );
        plusGithubUserWinCount(githubUser);
    }

    private void plusGithubUserWinCount(GithubUser githubUser) {
        githubUser.plusWindCount();
    }

    private GithubUser getGithubUser(String githubId) {
        return githubUserRepository.findById(githubId)
                .orElseThrow(() -> {
                    throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);
                });
    }
}
