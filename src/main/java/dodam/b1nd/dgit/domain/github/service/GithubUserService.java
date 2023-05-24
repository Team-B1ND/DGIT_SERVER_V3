package dodam.b1nd.dgit.domain.github.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubUserDto;
import dodam.b1nd.dgit.domain.github.repository.GithubUserRepository;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubUserService {

    private final ApolloClient apolloClient;
    private final GithubWeekService githubWeekService;
    private final GithubUserRepository githubUserRepository;

    @Transactional
    public void save(User user, GithubUserDto githubUserDto) {
        existUser(githubUserDto.getGithubId(), user.getId());
        GetUserQuery.Data data = getData(githubUserDto.getGithubId()).getData();

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
                .build();
    }

    private void existUser(final String githubId, final Long userId) {
        try {
            Optional.ofNullable(githubUserRepository.findByGithubIdOrUser_Id(githubId, userId).get(0))
                    .ifPresent(githubUser -> { throw CustomError.of(ErrorCode.GITHUB_USER_EXIST); });
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
        return githubUserRepository.findAll();
    }

    public UserDto existByUser(User user) {
        GithubUser githubUser = githubUserRepository.selectGithubUserByUser(user.getId());

        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userImage(githubUser == null ? null : githubUser.getUserImage())
                .githubId(githubUser == null ? null : githubUser.getGithubId())
                .build();
    }
    @Transactional
    public void update(User user, GithubUserDto githubUserDto) {
        GithubUser githubUser = githubUserRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> {throw CustomError.of(ErrorCode.GITHUB_USER_NOT_FOUND);});

        githubWeekService.removeWeek(githubUser.getGithubId());
        save(user, githubUserDto);
    }
}
