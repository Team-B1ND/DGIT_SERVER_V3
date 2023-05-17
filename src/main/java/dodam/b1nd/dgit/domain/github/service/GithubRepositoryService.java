package dodam.b1nd.dgit.domain.github.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import dodam.b1nd.dgit.domain.github.domain.entity.RepositoryOwner;
import dodam.b1nd.dgit.domain.github.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.repository.GithubRepositoryRepository;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import dodam.b1nd.dgit.global.lib.apolloclient.ApolloClientUtil;
import github.query.GetRepositoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubRepositoryService {

    private final ApolloClient apolloClient;
    private final GithubRepositoryRepository githubRepositoryRepository;

    public Response<GetRepositoryQuery.Data> getData(String githubId, String repositoryName) {
        Response<GetRepositoryQuery.Data> responseData = getResponseData(githubId, repositoryName);

        if (responseData.getData().repository() == null) {
            throw CustomError.of(ErrorCode.GITHUB_REPOSITORY_NOT_FOUND);
        }

        return responseData;
    }

    private Response<GetRepositoryQuery.Data> getResponseData(String githubId, String repositoryName) {
        return ApolloClientUtil.toCompletableFuture(apolloClient.query(
                        GetRepositoryQuery
                                .builder()
                                .name(repositoryName)
                                .owner(githubId)
                                .build()
                )
        ).join();
    }

    @Transactional(rollbackFor = Exception.class)
    public GithubRepository save(final User user, final AddGithubRepositoryDto request) {
        GetRepositoryQuery.Data data = getData(request.getGithubId(), request.getRepositoryName()).getData();

        return githubRepositoryRepository.save(GithubRepository.builder()
                .repositoryName(request.getRepositoryName())
                .totalStars(data.repository().stargazerCount())
                .user(user)
                .repositoryOwner(RepositoryOwner.builder()
                        .githubId(request.getGithubId())
                        .githubImage(data.repository().owner().avatarUrl().toString())
                        .build())
                .build());
    }

    @Transactional(readOnly = true)
    public List<GithubRepositoryDto> getRepositoryListSort() {
        return githubRepositoryRepository.findAllByOrderByTotalStarsDesc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private GithubRepositoryDto toDto(GithubRepository githubRepository) {
        return GithubRepositoryDto.builder()
                .repositoryName(githubRepository.getRepositoryName())
                .totalStars(githubRepository.getTotalStars())
                .githubId(githubRepository.getRepositoryOwner().getGithubId())
                .githubUserImage(githubRepository.getRepositoryOwner().getGithubImage())
                .build();
    }

    @Transactional(readOnly = true)
    public List<GithubRepository> getRepositoryList() {
        return githubRepositoryRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(GithubRepository githubRepository, GetRepositoryQuery.Repository repository) {
        githubRepositoryRepository.findById(githubRepository.getId()).get()
                .update(repository.stargazerCount());
    }
}