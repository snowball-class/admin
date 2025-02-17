package shop.snowballclass.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.snowballclass.admin.common.ApiResponse;
import shop.snowballclass.admin.dto.EventCreateRequest;
import shop.snowballclass.admin.dto.EventInfoResponse;
import shop.snowballclass.admin.dto.EventSimpleResponse;
import shop.snowballclass.admin.service.EventClassService;

@Tag(name = "어드민 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final EventClassService eventClassService;

    @Operation(summary = "이벤트 생성")
    @PostMapping("/event")
    public ApiResponse<EventSimpleResponse> createEvent(@Valid @RequestBody EventCreateRequest request) {
        return ApiResponse.created(eventClassService.createEventClass(request));
    }

    @Operation(summary = "이벤트 조회")
    @GetMapping("/event/{eventId}")
    public ApiResponse<EventInfoResponse> getEventInfo(@PathVariable("eventId") Long eventId) {
        return ApiResponse.success(eventClassService.getEventClassInfo(eventId));
    }

}
