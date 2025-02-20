package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.CountCompletedBookResponse;
import com.dnd.sbooky.api.docs.spec.CountBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountBookController implements CountBookApiSpec {

    private final CountBookUseCase countBookUseCase;

    /**
     * 주인이 등록한 책 중 완독한 도서의 수를 조회한다.
     */
    @GetMapping("/books/members/{ownerId}/completed/count")
    public ApiResponse<CountCompletedBookResponse> countCompletedBooks(
            @PathVariable Long ownerId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        Long visitorId = extractMemberId(user);
        return ApiResponse.success(countBookUseCase.countCompletedBooks(visitorId, ownerId));
    }

    private Long extractMemberId(UserDetails user) {
        return Optional.ofNullable(user).map(UserDetails::getUsername).map(Long::valueOf).orElse(null);
    }
}
