package dodam.b1nd.dgit.domain.github.githubrepository.presentation.controller;

import dodam.b1nd.dgit.domain.github.githubrepository.service.GithubRepositoryService;
import dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.request.AddGithubRepositoryDto;
import dodam.b1nd.dgit.domain.github.githubrepository.presentation.dto.response.GithubRepositoryDto;
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

@Tag(name = "GithubRepository", description = "GithubRepository Api")
@RestController
@RequestMapping(value = "/github-repository")
@RequiredArgsConstructor
public class GithubRepositoryController {

    private final GithubRepositoryService githubRepositoryService;

    @Operation(description = "Repository 추가")
    @AuthCheck
    @PostMapping
    public Response addRepository(@RequestAttribute User user, @RequestBody @Valid AddGithubRepositoryDto request) {
        githubRepositoryService.create(user, request);
        return Response.of(HttpStatus.OK, "깃허브 레포지토리 추가 성공");
    }

    @Operation(description = "Repository Star 순위 조회")
    @GetMapping
    public ResponseData<List<GithubRepositoryDto>> getRepositoryRank() {
        List<GithubRepositoryDto> repositoryList = githubRepositoryService.getRepositoryListSort();
        return ResponseData.of(HttpStatus.OK, "Repository 순위 조회 성공", repositoryList);
    }

    @AuthCheck
    @Operation(description = "Repository 삭제")
    @DeleteMapping(value = "/{id}")
    public Response deleteRepository(
            final @PathVariable long id
    ) {
        githubRepositoryService.deleteRepository(id);
        return Response.of(HttpStatus.OK, "Repository 삭제 성공");
    }
}
