package dodam.b1nd.dgit.domain.github.domain.entity;

import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.lib.jpa.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser extends BaseTimeEntity {

    @Id
    @Column(unique = true)
    private String githubId;

    @NotNull
    private int totalContributions;

    @NotNull
    private int pullRequest;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "fk_user_id")
    private User user;

    private String userImage;

    private String bio;

    public void update(int totalContributions, int pullRequest, String userImage, String bio) {
        this.totalContributions = totalContributions;
        this.pullRequest = pullRequest;
        this.userImage = userImage;
        this.bio = bio;
    }
}