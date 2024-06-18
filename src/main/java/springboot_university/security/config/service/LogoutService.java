package springboot_university.security.config.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import springboot_university.security.auth.service.CookieService;
import springboot_university.security.token.model.Token;
import springboot_university.security.token.repository.TokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final CookieService cookieService;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(

            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            Authentication authentication
    ) {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwt = authHeader.substring(7);

        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if(storedToken != null) {

            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        }

        cookieService.expireCookie(response, "jwt");
    }
}