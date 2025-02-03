package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.request.UpdateBookRequest;
import com.dnd.sbooky.api.docs.spec.UpdateBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UpdateBookController implements UpdateBookApiSpec {

    private final UpdateBookUseCase updateBookUseCase;

    @PutMapping("/books/{memberBookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> updateBook(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter @PathVariable Long memberBookId,
            @Valid @RequestBody UpdateBookRequest request) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        updateBookUseCase.update(memberId, memberBookId, request);

        return ApiResponse.success();
    }
}
