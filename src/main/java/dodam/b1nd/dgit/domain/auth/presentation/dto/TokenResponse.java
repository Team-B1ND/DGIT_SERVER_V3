package dodam.b1nd.dgit.domain.auth.presentation.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}