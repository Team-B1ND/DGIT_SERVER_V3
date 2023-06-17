package dodam.b1nd.dgit.global.lib.scheduler;

import dodam.b1nd.dgit.domain.github.githubweek.domain.entity.GithubWeek;
import dodam.b1nd.dgit.domain.github.githubrepository.service.GithubRepositoryService;
import dodam.b1nd.dgit.domain.github.githubuser.service.GithubUserService;
import dodam.b1nd.dgit.domain.github.githubweek.service.GithubWeekService;
import dodam.b1nd.dgit.domain.github.githubweek.service.WeekRankService;
import github.query.GetRepositoryQuery;
import github.query.GetUserQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final GithubUserService githubUserService;
    private final GithubWeekService githubWeekService;
    private final GithubRepositoryService githubRepositoryService;
    private final WeekRankService weekRankService;

    @Scheduled(cron = "0 0 1,8,10,12,14,16,18,20,23 * * *")
    public void userSchedule() {
        githubUserService.getGithubUserList().forEach(githubUser -> {
            try {
                GetUserQuery.User githubData = githubUserService.getData(githubUser.getGithubId()).getData().user();
                githubUserService.updateInfo(githubData);
                githubWeekService.updateInfo(githubUser, githubData);
            } catch (Exception e) {
                log.error("fail to process file", e);
            }
        });
    }

    @Scheduled(cron = "0 0 8,12,16,20,23 * * *")
    public void repositorySchedule() {
        githubRepositoryService.getRepositoryList().forEach(githubRepository -> {
            try {
                GetRepositoryQuery.Repository repository = githubRepositoryService.getData(
                        githubRepository.getRepositoryOwner().getGithubId(),
                        githubRepository.getRepositoryName()
                ).getData().repository();
                githubRepositoryService.updateInfo(githubRepository, repository);
            } catch (Exception e) {
                log.error("fail to process file", e);
            }
        });
    }

    @Scheduled(cron = "0 5 1 ? * 0")
    public void weekRankRecordSchedule() {
        GithubWeek week1st = githubWeekService.getWeek1st();
        weekRankService.saveRank(week1st);
        githubWeekService.setAllContributeZero();
    }
}
