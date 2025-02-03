package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.RegisterBookRequest;
import com.dnd.sbooky.api.docs.spec.RegisterBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegisterBookController implements RegisterBookApiSpec {

    private final RegisterBookUsecase registerBookUsecase;

    @PostMapping("/books")
    public ApiResponse<?> registerBook(
            @Valid @RequestBody RegisterBookRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        registerBookUsecase.registerBook(request, Long.valueOf(userDetails.getUsername()));
        return ApiResponse.success();
    }
}
