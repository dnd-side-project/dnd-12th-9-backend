package com.dnd.sbooky.api.book.v2;

import com.dnd.sbooky.api.book.v2.response.FindAllBookResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookCountResponseV2;
import com.dnd.sbooky.api.book.v2.response.FindBookDetailsResponseV2;
import com.dnd.sbooky.api.docs.spec.FindBookV2ApiSpec;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class FindBookControllerV2 implements FindBookV2ApiSpec {

    private final FindBookUseCaseV2 findBookUseCase;

    /**
     * 주인이 책장에 등록한 모든 도서를 조회한다.
     *
     * <p>ReadStatus 값 따라 조회 결과가 달라집니다. (대소문자 상관 X)
     * <li>default(null): 전체 조회
     * <li>want_to_read: 읽기 전 도서 조회
     * <li>reading: 읽는 중 도서 조회
     * <li>complete: 완독 도서 조회
     *
     * @param ownerId    조회할 회원 ID
     * @param readStatus 조회할 도서의 상태
     */
    @GetMapping("/members/{ownerId}/books")
    public ApiResponse<FindAllBookResponseV2> findBooks(
            @PathVariable Long ownerId,
            @RequestParam(required = false) ReadStatus readStatus,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        Long visitorId = extractMemberId(user);
        return ApiResponse.success(findBookUseCase.findAllMemberBooks(visitorId, ownerId, readStatus));
    }

    @GetMapping("/members/{ownerId}/books/count")
    public ApiResponse<FindBookCountResponseV2> findBookCount(
            @PathVariable Long ownerId,
            @RequestParam(required = false) ReadStatus readStatus,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user) {

        Long visitorId = extractMemberId(user);
        return ApiResponse.success(
                findBookUseCase.findBookCountByReadStatus(visitorId, ownerId, readStatus));
    }

    /**
     * 주인 책장에 등록된 도서의 상세 정보를 조회한다.
     *
     * @param memberBookId 조회할 도서 ID (member_book_id)
     * @param user         방문자 정보
     * @return 도서 상세 정보
     */
    @GetMapping("/books/{memberBookId}")
    public ApiResponse<FindBookDetailsResponseV2> findBookDetails(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @PathVariable Long memberBookId) {

        Long visitorId = extractMemberId(user);
        return ApiResponse.success(findBookUseCase.findBookDetails(memberBookId, visitorId));
    }

    /**
     * 사용자 정보에서 회원 ID를 추출한다.
     *
     * @param user 사용자 정보
     * @return 회원 ID (null: guest)
     */
    private Long extractMemberId(UserDetails user) {
        return Optional.ofNullable(user).map(UserDetails::getUsername).map(Long::valueOf).orElse(null);
    }
}
