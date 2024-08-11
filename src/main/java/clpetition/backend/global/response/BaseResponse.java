package clpetition.backend.global.response;

import clpetition.backend.global.docs.BaseResponseSchema;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import static clpetition.backend.global.response.BaseResponseStatus.SUCCESS;

@Data
@Builder
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> implements BaseResponseSchema<T> {

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T result;

    // Valid Handling
    public static <T> ResponseEntity<BaseResponse<T>> toResponseEntityContainsCustomMessage(BaseResponseStatus baseResponseStatus, String message) {
        return ResponseEntity
                .status(baseResponseStatus.getStatus())
                .body(BaseResponse.<T>builder()
                        .code(baseResponseStatus.getCode())
                        .message(message)
                        .result(null)
                        .build());
    }

    // Http Status만 Response로 반환하는 경우 (DELETE)
    public static <T> ResponseEntity<BaseResponse<T>> toResponseEntity(BaseResponseStatus baseResponseStatus) {
        return ResponseEntity
                .status(baseResponseStatus.getStatus())
                .body(null);
    }

    // Http Status만 Response로 반환하는 경우
    public static <T> ResponseEntity<BaseResponse<T>> toResponseEntityContainsStatus(BaseResponseStatus baseResponseStatus) {
        return ResponseEntity
                .status(baseResponseStatus.getStatus())
                .body(BaseResponse.<T>builder()
                        .code(baseResponseStatus.getCode())
                        .message(baseResponseStatus.getMessage())
                        .result(null)
                        .build());
    }

    // Http Status 200, Result가 존재하는 Response로 반환하는 경우
    public static <T> ResponseEntity<BaseResponse<T>> toResponseEntityContainsResult(T result) {
        return ResponseEntity
                .status(SUCCESS.getStatus())
                .body(BaseResponse.<T>builder()
                        .code(SUCCESS.getCode())
                        .message(SUCCESS.getMessage())
                        .result(result)
                        .build());
    }

    // 200 이외의 Status, Result가 존재하는 Response로 반환하는 경우
    public static <T> ResponseEntity<BaseResponse<T>> toResponseEntityContainsStatusAndResult(BaseResponseStatus baseResponseStatus, T result) {
        return ResponseEntity
                .status(baseResponseStatus.getStatus())
                .body(BaseResponse.<T>builder()
                        .code(baseResponseStatus.getCode())
                        .message(baseResponseStatus.getMessage())
                        .result(result)
                        .build());
    }
}
