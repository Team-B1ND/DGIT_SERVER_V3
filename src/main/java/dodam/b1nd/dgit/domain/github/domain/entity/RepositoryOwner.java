package dodam.b1nd.dgit.domain.github.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_repository_owner")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepositoryOwner {

    @Id
    @NotNull
    @Column(name = "github_id")
    private String githubId;

    @NotNull
    private String githubImage;

}