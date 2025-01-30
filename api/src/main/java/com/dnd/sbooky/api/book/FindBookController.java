package com.dnd.sbooky.api.book;

import com.dnd.sbooky.api.book.response.FindAllBookResponse;
import com.dnd.sbooky.api.docs.controller.FindBookControllerDocs;
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
public class FindBookController implements FindBookControllerDocs {

    private final FindBookUseCase findBookUseCase;

    @GetMapping("/books")
    public ApiResponse<FindAllBookResponse> searchBooks(
            @RequestParam(required = false, defaultValue = "all") String status,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        // todo: status (all, want, reading, complete)에 따라 다르게 처리해야할 것으로 보임. (프론트랑 같이 이야기하기)

        Long memberId = Long.parseLong(userDetails.getUsername());
        FindAllBookResponse response = findBookUseCase.findAllMemberBooks(memberId, ReadStatus.convert(status));

        return ApiResponse.success(response);
    }

}
