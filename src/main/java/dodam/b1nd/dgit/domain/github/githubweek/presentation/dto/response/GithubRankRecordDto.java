package dodam.b1nd.dgit.domain.github.githubweek.presentation.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubRankRecordDto {
    private LocalDate rankedDate;
    private String githubId;
    private String name;
    private int contributions;
    private String userImage;
    private String bio;
}
