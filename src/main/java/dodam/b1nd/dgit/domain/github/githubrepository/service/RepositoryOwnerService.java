package dodam.b1nd.dgit.domain.github.githubrepository.service;

import dodam.b1nd.dgit.domain.github.githubrepository.domain.entity.RepositoryOwner;
import dodam.b1nd.dgit.domain.github.githubrepository.repository.RepositoryOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepositoryOwnerService {

    private final RepositoryOwnerRepository ownerRepository;

    public RepositoryOwner existOwner(String githubId, String githubImage) {
        return ownerRepository.findByGithubId(githubId).orElseGet(
                () -> RepositoryOwner.builder()
                        .githubId(githubId)
                        .githubImage(githubImage)
                        .build()
        );
    }
}
