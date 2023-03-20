package dodam.b1nd.dgit.domain.user.domain.entity;

import dodam.b1nd.dgit.domain.githubuser.domain.entity.GithubUser;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private GithubUser githubUser;

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}