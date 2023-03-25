package dodam.b1nd.dgit.domain.github.repository;

import dodam.b1nd.dgit.domain.github.domain.entity.GithubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, String> {

    List<GithubRepository> findAllByOrderByTotalStarsDesc();

}