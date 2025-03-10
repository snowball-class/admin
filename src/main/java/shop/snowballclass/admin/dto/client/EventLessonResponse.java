package shop.snowballclass.admin.dto.client;

public record EventLessonResponse(
        Long id,
        Long eventId,
        Long lessonId
) {
}
