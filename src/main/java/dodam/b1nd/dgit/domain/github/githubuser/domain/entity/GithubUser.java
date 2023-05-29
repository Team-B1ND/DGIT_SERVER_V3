package dodam.b1nd.dgit.domain.github.githubuser.domain.entity;

import dodam.b1nd.dgit.domain.github.githubuser.domain.enums.AuthStatus;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private User user;

    private String userImage;

    private String bio;

    @NotNull
    private int winCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthStatus authStatus;

    public void update(int totalContributions, int pullRequest, String userImage, String bio) {
        this.totalContributions = totalContributions;
        this.pullRequest = pullRequest;
        this.userImage = userImage;
        this.bio = bio;
    }

    public void plusWindCount() {
        this.winCount += 1;
    }

    public void changeAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }
}