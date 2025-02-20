package shop.snowballclass.admin.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import shop.snowballclass.admin.dto.lesson.LessonResponse;
import shop.snowballclass.admin.entity.EventClass;

import java.time.LocalDateTime;
import java.util.List;

public record EventInfoResponse(
        @Schema(description = "이벤트 고유키", example = "1")
        Long id,
        @Schema(description = "이벤트 발행자", example = "전찬의")
        String issuer,
        @Schema(description = "이벤트 제목", example = "블랙 프라이데이 할인!")
        String title,
        @Schema(description = "이벤트 할인율", example = "25")
        Integer discountRate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 시작 시간", example = "2025-11-24T12:00:00")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 종료 시간", example = "2025-12-01T12:00:00")
        LocalDateTime endDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 생성일", example = "2025-02-18T12:30:16")
        LocalDateTime createdAt,
        @Schema(description = "이벤트 클래스 목록 (id)", example = "[\"1\", \"15\", \"6\"]")
        List<LessonResponse> classes
) {
    public static EventInfoResponse from(EventClass eventClass, List<LessonResponse> classes) {
        return new EventInfoResponse(
                eventClass.getId(),
                eventClass.getIssuer(),
                eventClass.getTitle(),
                eventClass.getDiscountRate(),
                eventClass.getStartDateTime(),
                eventClass.getEndDateTime(),
                eventClass.getCreatedAt(),
                classes
        );
    }
}
