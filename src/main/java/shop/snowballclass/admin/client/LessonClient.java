package shop.snowballclass.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.snowballclass.admin.common.ApiResponse;
import shop.snowballclass.admin.dto.lesson.LessonResponse;

import java.util.List;

@FeignClient(name = "lesson-service", url = "${feign.snowball.lesson.url}")
public interface LessonClient {
    @GetMapping("/event/{eventId}")
    ApiResponse<List<LessonResponse>> getEventLessonList(@PathVariable("eventId") Long eventId);

}
