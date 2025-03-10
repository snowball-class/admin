package shop.snowballclass.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import shop.snowballclass.admin.dto.ApiResponse;
import shop.snowballclass.admin.dto.client.EventLessonCreateRequest;
import shop.snowballclass.admin.dto.client.EventLessonResponse;

import java.util.List;

@FeignClient(name = "view-service", url = "${feign.snowball.view}")
public interface ViewClient {
    @PostMapping("/events/{eventId}/lessons")
    ApiResponse createEventLessons(@PathVariable("eventId") Long eventId, @RequestBody EventLessonCreateRequest lessonIds);

    @GetMapping("/lessons/bulk")
    ApiResponse<List<EventLessonResponse>> getEventLessonListByLessonIds(@RequestParam("ids") String lessonIds);

}
