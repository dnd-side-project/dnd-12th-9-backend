package com.dnd.sbooky.book;

import com.dnd.sbooky.book.request.RegisterBookRequest;
import com.dnd.sbooky.docs.controller.RegisterBookControllerDocs;
import com.dnd.sbooky.support.response.ApiResponse;
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
public class RegisterBookController implements RegisterBookControllerDocs {

    private final RegisterBookUsecase registerBookUsecase;
    @PostMapping("/book")
    public ApiResponse<?> registerBook(@Valid @RequestBody RegisterBookRequest request,
                                       @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {
        registerBookUsecase.registerBook(request, Long.valueOf(userDetails.getUsername()));
        return ApiResponse.success();
    }

}
