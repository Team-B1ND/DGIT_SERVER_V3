package dodam.b1nd.dgit.domain.github.githubrepository.repository;

import dodam.b1nd.dgit.domain.github.githubrepository.domain.entity.RepositoryOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryOwnerRepository extends JpaRepository<RepositoryOwner, Long> {
    Optional<RepositoryOwner> findByGithubId(String githubId);
}