package dodam.b1nd.dgit.domain.github.githubweek.service;

import dodam.b1nd.dgit.domain.github.githubuser.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
import dodam.b1nd.dgit.domain.github.githubweek.domain.entity.GithubWeek;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubRankDto;
import dodam.b1nd.dgit.domain.github.githubweek.repository.GithubWeekRepository;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import github.query.GetUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubWeekService {

    private final GithubWeekRepository githubWeekRepository;

    @Transactional(rollbackFor = Exception.class)
    public GithubWeek save(GetUserQuery.User githubData, GithubUser githubUser) {
        return githubWeekRepository.save(
                GithubWeek.builder()
                        .contribute(getContribute(githubData))
                        .githubUser(githubUser).build()
        );
    }

    private int getContribute(GetUserQuery.User githubData) {
        AtomicInteger contribute = new AtomicInteger();

        List<GetUserQuery.Week> weekCommit = githubData.contributionsCollection().contributionCalendar().weeks();
        if (weekCommit.size() == 0) return contribute.get();
        List<GetUserQuery.ContributionDay> contributeDays = weekCommit.get(weekCommit.size() - 1).contributionDays();

        contributeDays.forEach(contributeData -> contribute.addAndGet(contributeData.contributionCount()));
        return contribute.get();
    }

    public List<GithubRankDto> getWeekListSort() {
        List<GithubWeek> githubWeekList = githubWeekRepository.findAllByGithubUser_AuthStatus(Sort.by(Sort.Direction.DESC, "contribute"), AuthStatus.ALLOWED);

        List<GithubRankDto> result = githubWeekList.stream()
                .map(githubWeek ->
                        GithubRankDto.builder()
                                .githubId(githubWeek.getGithubUser().getGithubId())
                                .name(githubWeek.getGithubUser().getUser().getName())
                                .contributions(githubWeek.getContribute())
                                .userImage(githubWeek.getGithubUser().getUserImage())
                                .bio(githubWeek.getGithubUser().getBio()).build()
                ).collect(Collectors.toList());

        return result;
    }

    public GithubWeek getWeek1st() {
        return githubWeekRepository.findTopByOrderByContributeDesc().orElseThrow(() -> {
            throw CustomError.of(ErrorCode.INTERNAL_SERVER);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(GithubUser githubUser, GetUserQuery.User githubData) {

        int contribute = getContribute(githubData);

        GithubWeek githubWeek = githubWeekRepository.findByGithubUser(githubUser)
                .orElseThrow(() -> {throw CustomError.of(ErrorCode.WEEK_NOT_FOUND);});

        githubWeek.update(contribute);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeWeek(String githubId) {
        githubWeekRepository.deleteByGithubUser_GithubId(githubId);
    }
}
