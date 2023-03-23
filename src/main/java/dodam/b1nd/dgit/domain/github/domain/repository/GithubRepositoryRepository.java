package dodam.b1nd.dgit.domain.github.domain.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, String> {

    Optional<GithubRepository> findByGithubUser(GithubUser githubUser);

    Boolean existsByRepositoryName(String repositoryName);

    List<GithubRepository> findAllByOrderByTotalStarsDesc();

}