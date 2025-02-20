package shop.snowballclass.admin.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record EventUpdateRequest(
        @Size(min = 2, max = 30, message = "30자 이내로 입력해 주세요.")
        @Schema(description = "이벤트 제목", example = "변경할 데이터만 입력, 클래스 변경시 기존의 클래스까지 모두 입력")
        String title,
        @Min(value = 1, message = "할인율은 1 이상이어야 합니다.")
        @Max(value = 100, message = "할인율은 100%를 넘길 수 없습니다.")
        @Schema(description = "이벤트 할인율", example = "25")
        Integer discountRate,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 시작 시간", example = "2025-11-24T12:00:00")
        LocalDateTime startDateTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "이벤트 종료 시간", example = "2025-12-01T12:00:00")
        LocalDateTime endDateTime,
        @NotNull(message = "이벤트에 추가할 클래스들을 입력해 주세요.")
        @Size(min = 1, message = "이벤트는 하나 이상의 클래스를 포함해야 합니다.")
        @Schema(description = "이벤트 클래스 목록 (id)", example = "[1, 15, 6, 7, 2]")
        List<Long> classes
) {
}
