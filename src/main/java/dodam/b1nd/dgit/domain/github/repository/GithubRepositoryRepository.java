package dodam.b1nd.dgit.domain.github.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, Long> {
    @EntityGraph(attributePaths = "repositoryOwner")
    List<GithubRepository> findAllByOrderByTotalStarsDesc();

    @EntityGraph(attributePaths = "repositoryOwner")
    List<GithubRepository> findAll();

    Boolean existsByRepositoryName(String repositoryName);

}