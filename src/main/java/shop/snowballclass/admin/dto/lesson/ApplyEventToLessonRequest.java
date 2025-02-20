package shop.snowballclass.admin.dto.lesson;

import java.time.LocalDateTime;
import java.util.List;

public record ApplyEventToLessonRequest(
        Long eventId,
        Integer discountRate,
        LocalDateTime discountStartDate,
        LocalDateTime discountFinishDate,
        List<Long> lessonIds
) {
    public static ApplyEventToLessonRequest from(Long eventId,
                                                 Integer discountRate,
                                                 LocalDateTime discountStartDate,
                                                 LocalDateTime discountFinishDate,
                                                 List<Long> lessonIds) {
        return new ApplyEventToLessonRequest(
                eventId,
                discountRate,
                discountStartDate,
                discountFinishDate,
                lessonIds
        );
    }
}
