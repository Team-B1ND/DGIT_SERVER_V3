package dodam.b1nd.dgit.domain.githubuser.repository;

import dodam.b1nd.dgit.domain.githubuser.domain.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUser, String> {
    Optional<GithubUser> findById(String githubId);
}
