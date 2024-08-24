package dodam.b1nd.dgit.domain.github.githubuser.service;

import dodam.b1nd.dgit.domain.github.githubuser.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.request.GithubUserIdDto;
import dodam.b1nd.dgit.domain.github.githubuser.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.domain.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GithubUserServiceTest {

    @InjectMocks
    GithubUserService githubUserService;

    @Mock
    GithubUserRepository githubUserRepository;

    @Test
    void 유저허락() {

        //given
        GithubUserIdDto githubUserIdDto = githubUserIdDto();
        Optional<GithubUser> githubUser = githubUser();
        doReturn(githubUser).when(githubUserRepository).findById("test");

        //when
        githubUserService.allowGithubUser(githubUserIdDto);

        //then
        assertThat(githubUser.get().getAuthStatus()).isEqualTo(AuthStatus.ALLOWED);
    }

    private Optional<GithubUser> githubUser() {
        return Optional.ofNullable(GithubUser.builder()
                .githubId("test")
                .user(new User("test@gmail.com", "테스트", Role.STUDENT))
                .totalContributions(10)
                .pullRequest(10)
                .userImage(null)
                .bio(null)
                .winCount(0)
                .authStatus(AuthStatus.PENDING)
                .build());
    }

    private GithubUserIdDto githubUserIdDto() {
        return GithubUserIdDto.builder()
                .githubId("test")
                .build();
    }
}