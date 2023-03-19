package dodam.b1nd.dgit.domain.github.service;

import com.apollographql.apollo.ApolloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final ApolloClient apolloClient;

    public Response<GetContributionQuery.Data> getData(String userId) {
        Response<GetContributionQuery.Data> responseData = getResponseData(userId);

        if (responseData.getData().user() == null) {
            throw BadRequestErrorException.of("존재하지 않는 githubId 입니다");
        }

        return responseData;
    }

    private Response<GetContributionQuery.Data> getResponseData(@NotNull String userId) {
        return ApolloClientUtils.toCompletableFuture(githubApolloClient.query(
                        GetContributionQuery
                                .builder()
                                .login(userId)
                                .build())
                .toBuilder().build()
        ).join();
    }
}
