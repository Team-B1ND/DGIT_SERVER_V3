package dodam.b1nd.dgit.domain.auth.service;

import dodam.b1nd.dgit.domain.auth.presentation.dto.request.AdminLoginDto;
import dodam.b1nd.dgit.domain.auth.presentation.dto.response.TokenDto;
import dodam.b1nd.dgit.domain.token.service.TokenService;
import dodam.b1nd.dgit.global.error.CustomError;
import dodam.b1nd.dgit.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminRepository adminRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public TokenDto getToken(AdminLoginDto adminLoginDto) {

        Admin admin = adminRepository.findById(adminLoginDto.getId()).orElseThrow(() -> {
            throw CustomError.of(ErrorCode.ADMIN_NOT_FOUND);
        });

        if (checkPw(admin.getPw(), adminLoginDto.getPw())) {
            return TokenDto.builder()
                    .accessToken(tokenService.generateAccessToken(admin.getEmail()))
                    .refreshToken(tokenService.generateRefreshToken(admin.getEmail()))
                    .build();
        } else {
            throw CustomError.of(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    private boolean checkPw(String originPw, String verifyPw) {
        return passwordEncoder.matches(verifyPw, originPw);
    }
}
