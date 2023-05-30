package dodam.b1nd.dgit.domain.github.githubrepository.service;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import dodam.b1nd.dgit.domain.github.githubrepository.domain.entity.GithubRepository;
import dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.response.GithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.githubrepository.repository.GithubRepositoryRepository;
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

import static dodam.b1nd.dgit.global.error.ErrorCode.GITHUB_REPOSITORY_EXIST;

@Service
@RequiredArgsConstructor
public class GithubRepositoryService {

    private final ApolloClient apolloClient;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final RepositoryOwnerService ownerService;

    public Response<GetRepositoryQuery.Data> getData(String githubId, String repositoryName) {
        Response<GetRepositoryQuery.Data> responseData = getResponseData(githubId, repositoryName);

        if (responseData.getData().repository() == null) {
            throw CustomError.of(ErrorCode.GITHUB_REPOSITORY_NOT_FOUND);
        }

        return responseData;
    }

    @Transactional(rollbackFor = Exception.class)
    public GithubRepository create(final User user, final AddGithubRepositoryDto request) {
        GetRepositoryQuery.Data data = getData(request.getGithubId(), request.getRepositoryName()).getData();

        if(isExist(request.getRepositoryName())) {
            throw new CustomError(GITHUB_REPOSITORY_EXIST);
        }

        return githubRepositoryRepository.save(GithubRepository.builder()
                .repositoryName(request.getRepositoryName())
                .totalStars(data.repository().stargazerCount())
                .user(user)
                .repositoryOwner(ownerService.existOwner(
                        request.getGithubId(), data.repository().owner().avatarUrl().toString()))
                .build());
    }

    @Transactional(readOnly = true)
    public List<GithubRepositoryDto> getRepositoryListSort() {
        return githubRepositoryRepository.findAllByOrderByTotalStarsDesc().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

    protected boolean isExist(final String repositoryName) {
        return githubRepositoryRepository.existsByRepositoryName(repositoryName);
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

    private GithubRepositoryDto toDto(GithubRepository githubRepository) {
        return GithubRepositoryDto.builder()
                .repositoryName(githubRepository.getRepositoryName())
                .totalStars(githubRepository.getTotalStars())
                .githubId(githubRepository.getRepositoryOwner().getGithubId())
                .githubUserImage(githubRepository.getRepositoryOwner().getGithubImage())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRepository(long id) {
        GithubRepository githubRepository = githubRepositoryRepository.findById(id).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.GITHUB_REPOSITORY_NOT_FOUND);
        });

        githubRepositoryRepository.delete(githubRepository);
    }
}