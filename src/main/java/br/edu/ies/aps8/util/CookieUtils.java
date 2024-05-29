package br.edu.ies.aps8.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

public class CookieUtils {

    private CookieUtils() {
        throw new IllegalStateException("Utility Class");
    }

    public static Cookie getAuthCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(HttpHeaders.AUTHORIZATION))
                .findFirst()
                .orElse(null);
    }

}
