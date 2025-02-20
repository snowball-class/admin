package shop.snowballclass.admin.dto.lesson;

import java.time.LocalDateTime;

public record LessonResponse(
        Long lessonId,
        String title,
        Integer categoryId,
        Integer netPrice,
        Integer salePrice,
        String thumbnail,
        Integer eventId,
        Integer discountRate,
        LocalDateTime discountStartDate,
        LocalDateTime discountFinishDate
) {
}
