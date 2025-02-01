package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.docs.spec.FindBookApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
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
public class FindBookController implements FindBookApiSpec {

    private final FindBookUseCase findBookUseCase;

    /**
     * 회원의 도서 전체를 조회힙니다.
     *
     * <p>ReadStatus 값 따라 조회 결과가 달라집니다. (대소문자 상관 X)
     * <li>default(null): 전체 조회
     * <li>want_to_read: 읽고 싶은 도서 조회
     * <li>reading: 읽는 중인 도서 조회
     * <li>complete: 읽은 도서 조회
     *
     * @param readStatus 조회할 도서의 상태
     */
    @GetMapping("/books")
    public ApiResponse<FindAllBookResponse> searchBooks(
            @RequestParam(required = false) ReadStatus readStatus,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        Long memberId = Long.parseLong(userDetails.getUsername());

        return ApiResponse.success(findBookUseCase.findAllMemberBooks(memberId, readStatus));
    }
}
