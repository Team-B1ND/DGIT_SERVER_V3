package dodam.b1nd.dgit.domain.github.presentation.controller;

import dodam.b1nd.dgit.domain.github.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.request.GithubPullRequestDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRankDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.GithubUserDto;
import dodam.b1nd.dgit.domain.github.service.*;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.annotation.AuthCheck;
import dodam.b1nd.dgit.global.response.Response;
import dodam.b1nd.dgit.global.response.ResponseData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "GithubUser", description = "GithubUser Api")
@RestController
@RequestMapping(value = "/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubUserService githubUserService;
    private final GithubTotalService githubTotalService;
    private final GithubPullRequestService githubPullRequestService;
    private final GithubRepositoryService githubRepositoryService;
    private final GithubWeekService githubWeekService;

    @AuthCheck
    @PostMapping("/user")
    public Response modifyGithubId(
            final @RequestAttribute User user,
            final @Valid @RequestBody GithubUserDto githubUserDto
    ) {
        githubUserService.save(user, githubUserDto);
        return Response.of(HttpStatus.OK, "깃허브 계정 추가 성공");
    }

    @GetMapping("/total")
    public ResponseData<List<GithubRankDto>> getTotalRank() {
        List<GithubRankDto> totalRankList = githubTotalService.getTotalListSort();
        return ResponseData.of(HttpStatus.OK, "Commit 순위 조회 성공", totalRankList);
    }

    @GetMapping("/pull-request")
    public ResponseData<List<GithubPullRequestDto>> getPullRequestRank() {
        List<GithubPullRequestDto> pullRequestList = githubPullRequestService.getPullRequestListSort();
        return ResponseData.of(HttpStatus.OK, "Pull-Request 순위 조회 성공", pullRequestList);
    }

    @PostMapping("/repository")
    public Response addRepository(@RequestBody @Valid AddGithubRepositoryDto request) {
        githubRepositoryService.save(request);
        return Response.of(HttpStatus.OK, "깃허브 레포지토리 추가 성공");
    }

    @GetMapping("/repository")
    public ResponseData<List<GithubRepositoryDto>> getRepositoryRank() {
        List<GithubRepositoryDto> repositoryList = githubRepositoryService.getRepositoryListSort();
        return ResponseData.of(HttpStatus.OK, "Repository 순위 조회 성공", repositoryList);
    }

    @GetMapping("/week")
    public ResponseData<List<GithubRankDto>> getWeekRank() {
        List<GithubRankDto> weekList = githubWeekService.getWeekListSort();
        return ResponseData.of(HttpStatus.OK, "Week 순위 조회 성공", weekList);
    }
}
