package dodam.b1nd.dgit.domain.github.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUser, String> {
    Optional<GithubUser> findById(String githubId);

    @EntityGraph(attributePaths = "user")
    List<GithubUser> findAll(Sort sort);

    @Query("select u from GithubUser u where u.user.id=:userId")
    GithubUser selectGithubUserByUser(Long userId);

    Optional<GithubUser> findByUser_Id(Long userId);

    List<GithubUser> findByGithubIdOrUser_Id(String githubId, Long userId);
}
