package dodam.b1nd.dgit.domain.github.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubUserRepository extends JpaRepository<GithubUser, String> {
}
