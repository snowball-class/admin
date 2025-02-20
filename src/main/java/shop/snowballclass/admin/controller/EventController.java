package shop.snowballclass.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.snowballclass.admin.common.ApiResponse;
import shop.snowballclass.admin.dto.event.EventCreateRequest;
import shop.snowballclass.admin.dto.event.EventInfoResponse;
import shop.snowballclass.admin.dto.event.EventSimpleResponse;
import shop.snowballclass.admin.dto.event.EventUpdateRequest;
import shop.snowballclass.admin.service.EventClassService;

@Tag(name = "어드민 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/event")
public class EventController {
    private final EventClassService eventClassService;

    @Operation(summary = "이벤트 생성")
    @PostMapping
    public ApiResponse<EventSimpleResponse> createEvent(@Valid @RequestBody EventCreateRequest request) {
        return ApiResponse.created(eventClassService.createEventClass(request));
    }

    @Operation(summary = "이벤트 조회")
    @GetMapping("/{eventId}")
    public ApiResponse<EventInfoResponse> getEventInfo(@PathVariable("eventId") Long eventId) {
        return ApiResponse.success(eventClassService.getEventClassInfo(eventId));
    }

    @Operation(summary = "이벤트 수정")
    @PutMapping("/{eventId}")
    public ApiResponse updateEvent(@PathVariable("eventId") Long eventId, @Valid @RequestBody EventUpdateRequest request) {
        return ApiResponse.success(eventClassService.updateEventClass(eventId, request));
    }

    @Operation(summary = "이벤트 삭제")
    @DeleteMapping("/{eventId}")
    public ApiResponse deleteEvent(@PathVariable("eventId") Long eventId) {
        eventClassService.deleteEventClass(eventId);
        return ApiResponse.success();
    }

}
