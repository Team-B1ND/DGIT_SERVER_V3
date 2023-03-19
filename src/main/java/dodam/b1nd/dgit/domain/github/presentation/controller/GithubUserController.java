package dodam.b1nd.dgit.domain.github.presentation.controller;

import dodam.b1nd.dgit.domain.github.presentation.dto.GithubUserDto;
import dodam.b1nd.dgit.domain.github.service.GithubUserService;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.global.annotation.AuthCheck;
import dodam.b1nd.dgit.global.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "GithubUser", description = "GithubUser Api")
@RestController
@RequestMapping(value = "/github/user")
@RequiredArgsConstructor
public class GithubUserController {

    private final GithubUserService githubUserService;

    @AuthCheck
    @PostMapping
    public Response modifyGithubId(
            final @RequestAttribute User user,
            final @Valid @RequestBody GithubUserDto githubUserDto
    ) {
        githubUserService.save(user, githubUserDto);
        return Response.of(HttpStatus.OK, "깃허브 계정 추가 성공");
    }
}
