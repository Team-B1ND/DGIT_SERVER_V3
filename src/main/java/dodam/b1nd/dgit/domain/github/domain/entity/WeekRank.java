package dodam.b1nd.dgit.domain.github.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class WeekRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_rank_id", unique = true, nullable = false)
    private Long id;

    @NotNull
    private LocalDate rankedDate;

    @NotNull
    private int contribute;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_github_id", nullable = false)
    private GithubUser githubUser;

    @Builder
    public WeekRank(GithubUser githubUser, int contribute) {
        this.rankedDate = LocalDate.now().minusDays(6);
        this.githubUser = githubUser;
        this.contribute = contribute;
    }
}
