package dodam.b1nd.dgit.domain.github.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubUserDto;
import dodam.b1nd.dgit.domain.github.repository.GithubUserRepository;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GithubUserService {

    private final ApolloClient apolloClient;
    private final GithubWeekService githubWeekService;
    private final GithubUserRepository githubUserRepository;

    @Transactional
    public void save(User user, GithubUserDto githubUserDto) {
        existUser(githubUserDto.getGithubId());
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
                .build();
    }

    private void existUser(final String githubId) {
        githubUserRepository.findById(githubId)
                .ifPresent(githubUser -> CustomError.of(ErrorCode.GITHUB_USER_EXIST));
    }

    @Transactional
    public void update(final GetUserQuery.User githubUser) {
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
}
