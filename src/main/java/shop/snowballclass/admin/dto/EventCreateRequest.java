package shop.snowballclass.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateRequest(
        @Schema(description = "이벤트 발행자", example = "전찬의")
        String issuer,
        @Schema(description = "이벤트 제목", example = "블랙 프라이데이 할인!")
        String title,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 시작 시간", example = "2025-11-24T12:00:00")
        LocalDateTime startDateTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 종료 시간", example = "2025-12-01T12:00:00")
        LocalDateTime endDateTime,
        @Schema(description = "이벤트 클래스 목록", example = "[\"클래스 A\", \"클래스 B\", \"클래스 C\"]")
        List<String> classes
) {
}
