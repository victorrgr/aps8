package br.edu.ies.aps8.config.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static br.edu.ies.aps8.util.CookieUtils.getAuthCookie;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,
                       Authentication authentication) {
        final String authToken = getAuthToken(httpRequest);
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            return;
        }
        Cookie authCookie = new Cookie(HttpHeaders.AUTHORIZATION, null);
        authCookie.setMaxAge(0);
        authCookie.setPath("/api");
        authCookie.setHttpOnly(true);
        httpResponse.addCookie(authCookie);
        SecurityContextHolder.clearContext();
    }

    private String getAuthToken(HttpServletRequest request) {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        Cookie authCookie = getAuthCookie(request);
        if (authToken == null && authCookie != null) {
            authToken = Optional.ofNullable(authCookie.getValue())
                    .map(v -> URLDecoder.decode(v, StandardCharsets.UTF_8))
                    .orElse(null);
        }
        return authToken;
    }

}