package dodam.b1nd.dgit.domain.github.githubweek.repository;

import dodam.b1nd.dgit.domain.github.githubweek.domain.entity.WeekRank;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekRankRepository extends JpaRepository<WeekRank, Long> {

    @EntityGraph(attributePaths = {"githubUser", "githubUser.user"})
    List<WeekRank> findAllByOrderByRankedDateDesc(PageRequest pageRequest);
}
