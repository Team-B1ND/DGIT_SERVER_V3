package dodam.b1nd.dgit.domain.user.presentation.controller;

import dodam.b1nd.dgit.domain.github.githubuser.service.GithubUserService;
import dodam.b1nd.dgit.domain.user.domain.entity.User;
import dodam.b1nd.dgit.domain.user.presentation.dto.UserDto;
import dodam.b1nd.dgit.global.annotation.AuthCheck;
import dodam.b1nd.dgit.global.response.ResponseData;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User Api")
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final GithubUserService githubUserService;

    @AuthCheck
    @GetMapping("/my")
    public ResponseData<UserDto> getUser(@RequestAttribute User user) {
        UserDto userInfo = githubUserService.existByUser(user);
        return ResponseData.of(HttpStatus.OK, "자신의 정보 가져오기 성공", userInfo);
    }
}
