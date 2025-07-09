package dodam.b1nd.dgit.global.lib.scheduler;

import dodam.b1nd.dgit.domain.github.githubrepository.service.GithubRepositoryService;
import dodam.b1nd.dgit.domain.github.githubuser.service.GithubUserService;
import dodam.b1nd.dgit.domain.github.githubweek.service.GithubWeekService;
import dodam.b1nd.dgit.domain.github.githubweek.service.WeekRankService;
import dodam.b1nd.dgit.global.lib.scheduler.Scheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {

    @Mock
    private GithubUserService githubUserService;

    @Mock
    private GithubWeekService githubWeekService;

    @Mock
    private GithubRepositoryService githubRepositoryService;

    @Mock
    private WeekRankService weekRankService;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    void testUserSchedule() {
        when(githubUserService.getGithubUserList()).thenReturn(List.of());
        scheduler.userSchedule();
        verify(githubUserService, times(1)).getGithubUserList();
    }
}