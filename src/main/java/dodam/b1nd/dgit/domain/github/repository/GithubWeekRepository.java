package dodam.b1nd.dgit.domain.github.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.domain.entity.GithubWeek;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GithubWeekRepository extends JpaRepository<GithubWeek, Long> {
    Optional<GithubWeek> findByGithubUser(GithubUser githubUser);

    @EntityGraph(attributePaths = {"githubUser", "githubUser.user"})
    List<GithubWeek> findAll(Sort sort);
}
