package br.com.mjv.quizz.infrastructure.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class Cookies {

    public static void setCookies(HttpServletResponse response, String key, String value, int age) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    public static String getCookies(HttpServletRequest request, String key) {
        return Optional.ofNullable(request.getCookies()).stream()
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> key.equals(cookie.getName())))
                .findAny()
                .map(Cookie::getValue)
                .orElseThrow(RuntimeException::new);
    }

}
