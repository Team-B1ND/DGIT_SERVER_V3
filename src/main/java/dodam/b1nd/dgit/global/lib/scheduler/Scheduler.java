package dodam.b1nd.dgit.global.lib.scheduler;

import dodam.b1nd.dgit.domain.github.service.GithubUserService;
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

    @Scheduled(cron = "0 0 8,10,12,14,16,18,20,23 * * *")
    public void dailySchedule() {

        githubUserService.getGithubUserList().forEach(githubUser -> {
            try {
                GetUserQuery.User githubData = githubUserService.getData(githubUser.getGithubId()).getData().user();
                githubUserService.update(githubData);
            } catch (Exception e) {
                log.error("fail to process file", e);
            }
        });
    }
}
