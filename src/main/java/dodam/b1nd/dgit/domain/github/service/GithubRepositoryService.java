package dodam.b1nd.dgit.domain.github.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import dodam.b1nd.dgit.domain.github.facade.GithubUserFacade;
import dodam.b1nd.dgit.domain.github.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.domain.repository.GithubRepositoryRepository;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import dodam.b1nd.dgit.global.lib.apolloclient.ApolloClientUtil;
import github.query.GetRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubRepositoryService {

    private final ApolloClient apolloClient;
    private final GithubUserFacade githubUserFacade;
    private final GithubRepositoryRepository githubRepositoryRepository;

    private Response<GetRepositoryQuery.Data> getData(AddGithubRepositoryDto request) {
        Response<GetRepositoryQuery.Data> responseData = getResponseData(request);

        if (responseData.getData().repository() == null) {
            throw CustomError.of(ErrorCode.GITHUB_REPOSITORY_NOT_FOUND);
        }

        return responseData;
    }

    private Response<GetRepositoryQuery.Data> getResponseData(AddGithubRepositoryDto request) {
        return ApolloClientUtil.toCompletableFuture(apolloClient.query(
                        GetRepositoryQuery
                                .builder()
                                .name(request.getRepositoryName())
                                .owner(request.getGithubId())
                                .build()
                )
        ).join();
    }

    @Transactional(rollbackFor = Exception.class)
    public GithubRepository save(final AddGithubRepositoryDto request) {
        githubUserFacade.existsByGithubId(request.getGithubId());
        GetRepositoryQuery.Data data = getData(request).getData();

        return githubRepositoryRepository.save(GithubRepository.builder()
                .repositoryName(request.getRepositoryName())
                .githubUser(githubUserFacade.findByGithubId(request.getGithubId()))
                .totalStars(data.repository().stargazerCount())
                .build());
    }

    @Transactional(readOnly = true)
    public List<GithubRepositoryDto> getRepositoryListSort() {
        return githubRepositoryRepository.findAllByOrderByTotalStarsDesc().stream()
                .map(githubUserFacade::toDto)
                .toList();
    }

}