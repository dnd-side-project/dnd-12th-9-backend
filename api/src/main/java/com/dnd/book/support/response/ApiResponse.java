package com.dnd.book.support.response;


import com.dnd.book.support.error.ErrorMessage;
import com.dnd.book.support.error.ErrorType;
import lombok.Getter;

@Getter
public class ApiResponse<S> {

    private final ResultType resultType;
    private final S data;
    private final ErrorMessage error;


    private ApiResponse(ResultType resultType, S data, ErrorMessage error) {
        this.resultType = resultType;
        this.data = data;
        this.error = error;
    }


    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> error(ErrorType error) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
    }

    public static ApiResponse<?> error(ErrorType error, Object errorData) {
        return new ApiResponse<>(ResultType.ERROR, errorData, new ErrorMessage(error, errorData));
    }
}
