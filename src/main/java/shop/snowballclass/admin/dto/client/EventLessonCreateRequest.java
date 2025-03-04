package shop.snowballclass.admin.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EventLessonCreateRequest(
        @NotNull(message = "할인 이벤트에 추가할 클래스를 하나 이상 입력해 주세요.")
        @Size(min = 1, message = "할인 이벤트는 하나 이상의 클래스를 포함해야 합니다.")
        @Schema(description = "이벤트 할인 적용할 클래스 목록 (id)", example = "[1, 15, 6, 7, 2]")
        List<Long> lessonIds
) {
        public static EventLessonCreateRequest from(List<Long> lessonIds) {
                return new EventLessonCreateRequest(
                        lessonIds
                );
        }
}
