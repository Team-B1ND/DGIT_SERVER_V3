package dodam.b1nd.dgit.domain.github.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull
    private String repositoryName;

    @NotNull
    private int totalStars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_github_id", nullable = false)
    private GithubUser githubUser;

}