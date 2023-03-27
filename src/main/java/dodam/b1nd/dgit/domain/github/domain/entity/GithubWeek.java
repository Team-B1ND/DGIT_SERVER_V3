package dodam.b1nd.dgit.domain.github.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class GithubWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_id", unique = true, nullable = false)
    private Long id;

    @NotNull
    private int contribute;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_github_id", nullable = false)
    private GithubUser githubUser;

    @Builder
    public GithubWeek(int contribute, GithubUser githubUser) {
        this.contribute = contribute;
        this.githubUser = githubUser;
    }

    public void update(int contribute) {
        this.contribute = contribute;
    }
}
