package dodam.b1nd.dgit.domain.github.domain.entity;

import dodam.b1nd.dgit.domain.user.domain.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_github_repository")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubRepository {

    @Id
    @NotNull
    private String repositoryName;

    @NotNull
    private int totalStars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_github_id", nullable = false)
    private RepositoryOwner repositoryOwner;

}