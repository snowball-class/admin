package shop.snowballclass.admin.common;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        @Schema(description = "response status", example = "OK")
        HttpStatus status,
        @Schema(description = "response message", example = "any message")
        String message,
        T data
) {
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(HttpStatus.OK, null, null);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(HttpStatus.OK, message, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, null, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED, null, data);
    }

    public static <T> ApiResponse<T> accepted(T data) {
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, data);
    }

    public boolean isOK() {
        if(HttpStatus.OK.equals(status)) return true;
        else return false;
    }
}
