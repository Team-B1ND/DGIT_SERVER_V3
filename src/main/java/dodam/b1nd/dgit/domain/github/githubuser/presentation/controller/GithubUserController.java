package dodam.b1nd.dgit.domain.github.githubuser.presentation.controller;

import dodam.b1nd.dgit.domain.github.githubuser.service.GithubPullRequestService;
import dodam.b1nd.dgit.domain.github.githubuser.service.GithubTotalService;
import dodam.b1nd.dgit.domain.github.githubuser.service.GithubUserService;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubPullRequestDto;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubRankDto;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.request.GithubUserDto;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.annotation.AuthCheck;
import dodam.b1nd.dgit.global.response.Response;
import dodam.b1nd.dgit.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "GithubUser", description = "GithubUser Api")
@RestController
@RequestMapping(value = "/github-user")
@RequiredArgsConstructor
public class GithubUserController {

    private final GithubUserService githubUserService;
    private final GithubTotalService githubTotalService;
    private final GithubPullRequestService githubPullRequestService;

    @Operation(description = "Github 계정 추가")
    @AuthCheck
    @PostMapping
    public Response createGithubUser(
            final @RequestAttribute User user,
            final @Valid @RequestBody GithubUserDto githubUserDto
    ) {
        githubUserService.save(user, githubUserDto);
        return Response.of(HttpStatus.OK, "깃허브 계정 추가 성공");
    }

    @Operation(description = "Github 계정 수정")
    @AuthCheck
    @PatchMapping
    public Response modifyGithubUser(
            final @RequestAttribute User user,
            final @Valid @RequestBody GithubUserDto githubUserDto
    ) {
        githubUserService.update(user, githubUserDto);
        return Response.of(HttpStatus.OK, "깃허브 계정 수정 성공");
    }

    @Operation(description = "Commit 순위 조회")
    @GetMapping("/total")
    public ResponseData<List<GithubRankDto>> getTotalRank() {
        List<GithubRankDto> totalRankList = githubTotalService.getTotalListSort();
        return ResponseData.of(HttpStatus.OK, "Commit 순위 조회 성공", totalRankList);
    }

    @Operation(description = "Pull Request 순위 조회")
    @GetMapping("/pull-request")
    public ResponseData<List<GithubPullRequestDto>> getPullRequestRank() {
        List<GithubPullRequestDto> pullRequestList = githubPullRequestService.getPullRequestListSort();
        return ResponseData.of(HttpStatus.OK, "Pull-Request 순위 조회 성공", pullRequestList);
    }
}
