package com.dnd.sbooky.api.member;

import com.dnd.sbooky.api.docs.spec.AddLikeApiSpec;
import com.dnd.sbooky.api.member.reqeust.AddLikeRequest;
import com.dnd.sbooky.api.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddLikeController implements AddLikeApiSpec {

    private final AddLikeUsecase addLikeUsecase;

    @PatchMapping("/likes")
    public ApiResponse<?> addLikes(@Valid @RequestBody AddLikeRequest request) {
        return ApiResponse.success(addLikeUsecase.add(request.memberId(), request.addCount()));
    }
}
