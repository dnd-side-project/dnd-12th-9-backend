package com.dnd.book.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import com.dnd.book.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    @GetMapping("/login")
    public ApiResponse<?> login(String accessToken, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append(TokenKey.TOKEN_PREFIX).append(accessToken);
        response.setHeader(AUTHORIZATION, sb.toString());
        return ApiResponse.success();
    }
}
