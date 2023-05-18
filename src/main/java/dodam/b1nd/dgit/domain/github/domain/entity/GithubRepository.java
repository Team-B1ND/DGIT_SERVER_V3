package dodam.b1nd.dgit.domain.github.domain.entity;

import dodam.b1nd.dgit.domain.user.domain.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repository_id")
    private Long id;

    @NotNull
    private String repositoryName;

    @NotNull
    private int totalStars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_owner_id", nullable = false)
    private RepositoryOwner repositoryOwner;

    public void update(int totalStars) {
        this.totalStars = totalStars;
    }
}