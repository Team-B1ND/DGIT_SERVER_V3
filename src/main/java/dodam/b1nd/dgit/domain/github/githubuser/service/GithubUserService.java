package dodam.b1nd.dgit.domain.github.githubuser.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.githubuser.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.request.GithubUserIdDto;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubUserDto;
import dodam.b1nd.dgit.domain.github.githubuser.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.github.githubweek.service.GithubWeekService;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.presentation.dto.UserDto;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import dodam.b1nd.dgit.global.lib.apolloclient.ApolloClientUtil;
import github.query.GetUserQuery;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubUserService {

    private final ApolloClient apolloClient;
    private final GithubWeekService githubWeekService;
    private final GithubUserRepository githubUserRepository;

    @Transactional
    public void save(User user, GithubUserIdDto githubUserIdDto) {
        existUser(githubUserIdDto.getGithubId(), user.getId());
        GetUserQuery.Data data = getData(githubUserIdDto.getGithubId()).getData();

        GithubUser githubUser = githubUserRepository.save(githubUserResponseToEntity(user, data.user()));
        githubWeekService.save(data.user(), githubUser);
    }

    public Response<GetUserQuery.Data> getData(String userId) {
        Response<GetUserQuery.Data> responseData = getResponseData(userId);
        if (responseData.getData().user() == null) {
            throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);
        }
        return responseData;
    }

    private <T> Response<GetUserQuery.Data> getResponseData(@NotNull String userId) {
        return ApolloClientUtil.toCompletableFuture(apolloClient.query(
                GetUserQuery
                        .builder()
                        .login(userId)
                        .build())
        ).join();
    }

    private GithubUser githubUserResponseToEntity(final User user, @NonNull GetUserQuery.User githubUser) {
        return GithubUser.builder()
                .githubId(githubUser.login())
                .user(user)
                .totalContributions(githubUser.contributionsCollection().contributionCalendar().totalContributions())
                .pullRequest(githubUser.pullRequests().totalCount())
                .userImage(githubUser.avatarUrl().toString())
                .bio(githubUser.bio())
                .winCount(0)
                .authStatus(AuthStatus.PENDING)
                .build();
    }

    private void existUser(final String githubId, final Long userId) {
        try {
            Optional.ofNullable(githubUserRepository.findByGithubIdOrUser_Id(githubId, userId).get(0))
                    .ifPresent(githubUser -> {
                        if (githubUser.getAuthStatus().equals(AuthStatus.PENDING)) throw CustomError.of(ErrorCode.GITHUB_USER_PENDING);
                        throw CustomError.of(ErrorCode.GITHUB_USER_EXIST);
                    });
        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    @Transactional
    public void updateInfo(final GetUserQuery.User githubUser) {
        GithubUser user = githubUserRepository.findById(githubUser.login()).get();
        user.update(
                githubUser.contributionsCollection().contributionCalendar().totalContributions(),
                githubUser.pullRequests().totalCount(),
                githubUser.avatarUrl().toString(),
                githubUser.bio()
        );
    }

    public List<GithubUser> getGithubUserList() {
        return githubUserRepository.findAllByAuthStatus(AuthStatus.ALLOWED);
    }

    public UserDto existByUser(User user) {
        GithubUser githubUser = githubUserRepository.selectGithubUserByUser(user.getId(), AuthStatus.ALLOWED);

        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userImage(githubUser == null ? null : githubUser.getUserImage())
                .githubId(githubUser == null ? null : githubUser.getGithubId())
                .role(user.getRole())
                .build();
    }
    @Transactional
    public void update(User user, GithubUserIdDto githubUserIdDto) {
        GithubUser githubUser = githubUserRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> {throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);});

        githubWeekService.removeWeek(githubUser.getGithubId());
        save(user, githubUserIdDto);
    }

    public List<GithubUserDto> getPendingUser() {
        List<GithubUser> githubUserList = githubUserRepository.findAllByAuthStatus(AuthStatus.PENDING);
        return githubUserList.stream().map(
                githubUser -> GithubUserDto.builder()
                        .githubId(githubUser.getGithubId())
                        .userImage(githubUser.getUserImage())
                        .name(githubUser.getUser().getName())
                        .email(githubUser.getUser().getEmail())
                        .bio(githubUser.getBio())
                        .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public void allowGithubUser(GithubUserIdDto githubUserIdDto) {
        GithubUser githubUser = githubUserRepository.findById(githubUserIdDto.getGithubId()).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);
        });
        githubUser.changeAuthStatus(AuthStatus.ALLOWED);
    }

    @Transactional
    public void denyGithubUser(GithubUserIdDto githubUserIdDto) {
        GithubUser githubUser = githubUserRepository.findById(githubUserIdDto.getGithubId()).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);
        });
        githubWeekService.removeWeek(githubUser.getGithubId());
        githubUserRepository.deleteById(githubUser.getGithubId());
    }
}
