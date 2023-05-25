package dodam.b1nd.dgit.domain.github.presentation.controller;

import dodam.b1nd.dgit.domain.github.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.request.GithubPullRequestDto;
import dodam.b1nd.dgit.domain.github.presentation.dto.response.*;
import dodam.b1nd.dgit.domain.github.service.*;
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

@Tag(name = "Github", description = "Github Api")
@RestController
@RequestMapping(value = "/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubUserService githubUserService;
    private final GithubTotalService githubTotalService;
    private final GithubPullRequestService githubPullRequestService;
    private final GithubRepositoryService githubRepositoryService;
    private final GithubWeekService githubWeekService;
    private final WeekRankService weekRankService;

    @Operation(description = "Github 계정 추가")
    @AuthCheck
    @PostMapping("/user")
    public Response createGithubUser(
            final @RequestAttribute User user,
            final @Valid @RequestBody GithubUserDto githubUserDto
    ) {
        githubUserService.save(user, githubUserDto);
        return Response.of(HttpStatus.OK, "깃허브 계정 추가 성공");
    }

    @Operation(description = "Github 계정 수정")
    @AuthCheck
    @PatchMapping("/user")
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

    @Operation(description = "Repository 추가")
    @AuthCheck
    @PostMapping("/repository")
    public Response addRepository(@RequestAttribute User user, @RequestBody @Valid AddGithubRepositoryDto request) {
        githubRepositoryService.create(user, request);
        return Response.of(HttpStatus.OK, "깃허브 레포지토리 추가 성공");
    }

    @Operation(description = "Repository Star 순위 조회")
    @GetMapping("/repository")
    public ResponseData<List<GithubRepositoryDto>> getRepositoryRank() {
        List<GithubRepositoryDto> repositoryList = githubRepositoryService.getRepositoryListSort();
        return ResponseData.of(HttpStatus.OK, "Repository 순위 조회 성공", repositoryList);
    }

    @Operation(description = "Weekly Commit 순위 조회")
    @GetMapping("/week")
    public ResponseData<List<GithubRankDto>> getWeekRank() {
        List<GithubRankDto> weekList = githubWeekService.getWeekListSort();
        return ResponseData.of(HttpStatus.OK, "Week 순위 조회 성공", weekList);
    }

    @Operation(description = "역대 Week 랭킹 조회")
    @GetMapping("/week/rank")
    public ResponseData<List<GithubRankRecordDto>> getRankRecord(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit
    ) {
        List<GithubRankRecordDto> rankRecordList = weekRankService.getRankRecord(page, limit);
        return ResponseData.of(HttpStatus.OK, "역대 랭킹 조회 성공", rankRecordList);
    }

    @Operation(description = "Week 랭킹 TOP3 조회")
    @GetMapping("/week/top")
    public ResponseData<List<GithubTopDto>> getRankTop3() {
        List<GithubTopDto> rankTop = weekRankService.getRankTop();
        return ResponseData.of(HttpStatus.OK, "Week 랭킹 TOP3 조회 성공", rankTop);
    }
}
