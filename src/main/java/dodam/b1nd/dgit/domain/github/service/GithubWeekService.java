package dodam.b1nd.dgit.domain.github.service;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubWeek;
import dodam.b1nd.dgit.domain.github.presentation.dto.GithubRankDto;
import dodam.b1nd.dgit.domain.github.repository.GithubWeekRepository;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import github.query.GetUserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubWeekService {

    private final GithubWeekRepository githubWeekRepository;

    @Transactional
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
        return null;
    }

    @Transactional
    public void update(GetUserQuery.User githubData) {

        int contribute = getContribute(githubData);

        GithubWeek githubWeek = githubWeekRepository.findByGithubUser(githubData.login())
                .orElseThrow(() -> {throw CustomError.of(ErrorCode.WEEK_NOT_FOUND);});

        githubWeek.update(contribute);
    }
}
