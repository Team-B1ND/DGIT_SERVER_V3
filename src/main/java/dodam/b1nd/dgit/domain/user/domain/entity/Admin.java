package dodam.b1nd.dgit.domain.user.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String pw;

    @NotNull
    private String email;
}
