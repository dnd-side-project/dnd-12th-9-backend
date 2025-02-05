package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.docs.spec.DeleteBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeleteBookController implements DeleteBookApiSpec {

    private final DeleteBookUseCase deleteBookUseCase;

    @DeleteMapping("/books/{memberBookId}")
    public ApiResponse<?> deleteBook(
            @PathVariable Long memberBookId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        deleteBookUseCase.delete(extractMemberId(user), memberBookId);
        return ApiResponse.success();
    }

    private Long extractMemberId(UserDetails user) {
        return Long.parseLong(user.getUsername());
    }
}
