package dodam.b1nd.dgit.domain.user.domain.entity;

import dodam.b1nd.dgit.domain.githubuser.domain.entity.GithubUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotNull
    private String name;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private GithubUser githubUser;

}