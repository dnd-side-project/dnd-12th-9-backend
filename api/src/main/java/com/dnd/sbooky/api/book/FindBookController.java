package com.dnd.sbooky.api.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.book.response.FindBookDetailsResponse;
import com.dnd.sbooky.api.docs.controller.FindBookControllerDocs;
import com.dnd.sbooky.api.support.response.ApiResponse;
import com.dnd.sbooky.core.book.ReadStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FindBookController implements FindBookControllerDocs {

    private final FindBookUseCase findBookUseCase;

    /**
     * 주인이 책장에 등록한 모든 도서를 조회한다.
     *
     * <p>ReadStatus 값 따라 조회 결과가 달라집니다. (대소문자 상관 X)
     * <li>default(null): 전체 조회
     * <li>want_to_read: 읽기 전 도서 조회
     * <li>reading: 읽는 중 도서 조회
     * <li>complete: 완독 도서 조회
     *
     * @param memberId 조회할 회원 ID
     * @param readStatus 조회할 도서의 상태
     */
    @GetMapping("/library/{memberId}")
    public ApiResponse<FindAllBookResponse> findBooks(
            @PathVariable Long memberId, @RequestParam(required = false) ReadStatus readStatus) {

        return ApiResponse.success(findBookUseCase.findAllMemberBooks(memberId, readStatus));
    }

    /**
     * 주인 책장에 등록된 도서의 상세 정보를 조회한다.
     *
     * @param memberBookId 조회할 도서 ID (member_book_id)
     * @return 도서 상세 정보
     */
    @GetMapping("/library/books/{memberBookId}")
    public ApiResponse<FindBookDetailsResponse> findBookDetails(@PathVariable Long memberBookId) {

        return ApiResponse.success(findBookUseCase.findBookDetails(memberBookId));
    }
}
