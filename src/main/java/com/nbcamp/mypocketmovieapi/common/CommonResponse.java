package com.nbcamp.mypocketmovieapi.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // 반환되는 필드가 null일경우, json으로 바꿀 때 제외시킴 -> 왜? 없는 데이터를 반환할 필요가 없으니까!
public class CommonResponse<T> {
    private final boolean success;
    private final int code;
    private final String status;
    private final String message;
    private final T data; // list 도 들어올수있고, class도 ... 모든 타입 다!

    @Builder // 생성 패턴임
    public CommonResponse(boolean success, int code, String status, String message, T data) {
        this.success = success;
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 성공버전, 제네릭타입으로 할거면 양 옆 <T>
    public static <T> CommonResponse<T> success(CommonCode code, T data) {
        return CommonResponse.<T>builder() // 빌더도 <T> 캐스팅
                //. 뒤에는 필드임!
                .success(true)
                .code(code.getCode())
                .status(code.getStatus())
                .message(code.getMessage())
                .data(data)
                .build();
    }

    // 데이터 없는 성공 버전
    public static <T> CommonResponse<T> success(CommonCode code) {
        return CommonResponse.<T>builder() // 빌더도 <T> 캐스팅
                .success(true)
                .code(code.getCode())
                .status(code.getStatus())
                .message(code.getMessage())
                .build();
    }

    // 실패 버전
    public static <T> CommonResponse<T> fail(CommonCode code) {
        return CommonResponse.<T>builder() // 빌더도 <T> 캐스팅
                .success(false)
                .code(code.getCode())
                .status(code.getStatus())
                .message(code.getMessage())
                .build();
    }

}
