package com.dnd.book.security;

import com.dnd.book.support.error.ApiException;
import com.dnd.book.support.error.ErrorMessage;
import com.dnd.book.support.response.ApiResponse;
import com.dnd.book.support.response.ResultType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class TokenExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            handleApiException(response, e);
        }
    }
    private void handleApiException(HttpServletResponse response, ApiException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.isCommitted();
        objectMapper.writeValue(response.getWriter(),
                new ApiResponse(ResultType.ERROR, null, new ErrorMessage(e.getErrorType())));
    }
}
