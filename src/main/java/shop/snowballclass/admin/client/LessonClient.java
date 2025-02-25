package shop.snowballclass.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.snowballclass.admin.common.ApiResponse;
import shop.snowballclass.admin.dto.lesson.ApplyEventToLessonRequest;
import shop.snowballclass.admin.dto.lesson.LessonResponse;

import java.util.List;

@FeignClient(name = "lesson-service", url = "${feign.snowball.lesson.url}")
public interface LessonClient {
    @GetMapping("/event/{eventId}")
    ApiResponse<List<LessonResponse>> getEventInfoOfLessons(@PathVariable("eventId") Long eventId);
    @PutMapping("/event")
    ApiResponse applyEventToLesson(@RequestBody ApplyEventToLessonRequest request);
    @DeleteMapping("/event/{eventId}")
    ApiResponse deleteEventFromLesson(@PathVariable("eventId") Long eventId);
}
