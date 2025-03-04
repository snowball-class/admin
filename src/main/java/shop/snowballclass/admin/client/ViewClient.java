package shop.snowballclass.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.snowballclass.admin.dto.ApiResponse;
import shop.snowballclass.admin.dto.client.EventLessonCreateRequest;

@FeignClient(name = "view-service", url = "${feign.snowball.view}")
public interface ViewClient {
    @PostMapping("/events/{eventId}/lessons")
    ApiResponse createEventLessons(@PathVariable("eventId") Long eventId, @RequestBody EventLessonCreateRequest lessonIds);
}
