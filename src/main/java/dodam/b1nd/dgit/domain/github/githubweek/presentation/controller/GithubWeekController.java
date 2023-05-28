package dodam.b1nd.dgit.domain.github.githubweek.presentation.controller;

import dodam.b1nd.dgit.domain.github.githubweek.service.GithubWeekService;
import dodam.b1nd.dgit.domain.github.githubweek.service.WeekRankService;
import dodam.b1nd.dgit.domain.github.githubuser.presentation.dto.response.GithubRankDto;
import dodam.b1nd.dgit.domain.github.githubweek.presentation.dto.response.GithubRankRecordDto;
import dodam.b1nd.dgit.domain.github.githubweek.presentation.dto.response.GithubTopDto;
import dodam.b1nd.dgit.global.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "GithubWeek", description = "GithubWeek Api")
@RestController
@RequestMapping(value = "/github-week")
@RequiredArgsConstructor
public class GithubWeekController {

    private final GithubWeekService githubWeekService;
    private final WeekRankService weekRankService;

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
