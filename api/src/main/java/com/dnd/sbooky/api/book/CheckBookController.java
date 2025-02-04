package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.CheckBookResponse;
import com.dnd.sbooky.api.docs.spec.CheckBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckBookController implements CheckBookApiSpec {

    private final CheckBookUseCase checkBookUseCase;

    @GetMapping("/books/check")
    public ApiResponse<CheckBookResponse> checkBookExist(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @RequestParam("title") String title,
            @RequestParam("author") String author) {

        Long memberId = Long.valueOf(user.getUsername());
        return ApiResponse.success(checkBookUseCase.check(memberId, title, author));
    }
}
