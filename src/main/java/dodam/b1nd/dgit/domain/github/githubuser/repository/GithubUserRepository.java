package dodam.b1nd.dgit.domain.github.githubuser.repository;

import dodam.b1nd.dgit.domain.github.githubuser.domain.entity.GithubUser;
import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
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
    List<GithubUser> findAllByAuthStatus(AuthStatus authStatus);

    @EntityGraph(attributePaths = "user")
    List<GithubUser> findAllByAuthStatusOrderByPullRequestDesc(AuthStatus authStatus);

    @EntityGraph(attributePaths = "user")
    List<GithubUser> findAllByAuthStatusOrderByTotalContributionsDesc(AuthStatus authStatus);

    @Query("select u from GithubUser u where u.user.id=:userId and u.authStatus=:authStatus")
    GithubUser selectGithubUserByUser(Long userId, AuthStatus authStatus);

    Optional<GithubUser> findByUser_Id(Long userId);

    List<GithubUser> findByGithubIdOrUser_Id(String githubId, Long userId);

    @EntityGraph(attributePaths = "user")
    List<GithubUser> findTop3ByAuthStatusOrderByWinCountDesc(AuthStatus authStatus);
}
