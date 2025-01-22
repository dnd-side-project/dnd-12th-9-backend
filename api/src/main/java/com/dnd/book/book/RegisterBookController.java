package com.dnd.book.book;

import com.dnd.book.book.request.RegisterBookRequest;
import com.dnd.book.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegisterBookController {

    private final RegisterBookUsecase registerService;

    @PostMapping("/book")
    public ApiResponse<?> registerBook(@Valid @RequestBody RegisterBookRequest request) {
        Long memberId = 1L; // 임시
        registerService.registerBook(request, memberId);
        return ResponseEntity.ok().build();
    }



}
