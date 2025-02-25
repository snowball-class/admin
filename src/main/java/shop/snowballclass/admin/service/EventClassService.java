package shop.snowballclass.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import shop.snowballclass.admin.client.LessonClient;
import shop.snowballclass.admin.common.ApiResponse;
import shop.snowballclass.admin.dto.event.EventCreateRequest;
import shop.snowballclass.admin.dto.event.EventInfoResponse;
import shop.snowballclass.admin.dto.event.EventSimpleResponse;
import shop.snowballclass.admin.dto.event.EventUpdateRequest;
import shop.snowballclass.admin.dto.lesson.ApplyEventToLessonRequest;
import shop.snowballclass.admin.dto.lesson.LessonResponse;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.repository.EventClassRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventClassService {
    private final EventClassRepository eventClassRepository;
    private final LessonClient lessonClient;

    @Transactional(readOnly = true)
    public EventClass getEventClassById(Long id) {
        return eventClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이벤트입니다."));
    }

    @Transactional
    public EventSimpleResponse createEventClass(EventCreateRequest request) {
        validateEventDateTime(request.startDateTime(), request.endDateTime());

        EventClass eventClass = eventClassRepository.save(EventClass.from(request));
        ApiResponse response = lessonClient.applyEventToLesson(ApplyEventToLessonRequest.from(
                eventClass.getId(), request.discountRate(),
                request.startDateTime(), request.endDateTime(),
                request.classes()));
        if (!response.checkStatusOK()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("[LessonClient] Failed to apply event to lesson. Status: " + response.status());
        }
        return EventSimpleResponse.from(eventClass);
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long eventClassId) {
        EventClass eventInfo = getEventClassById(eventClassId);
        ApiResponse<List<LessonResponse>> response = lessonClient.getEventInfoOfLessons(eventClassId);
        if(!response.checkStatusOK())
            throw new RuntimeException("[LessonClient] Failed to get event Info of lessons. Status: " + response.status());
        return EventInfoResponse.from(eventInfo, response.data());
    }

    @Transactional
    public Long updateEventClass(Long eventClassId, EventUpdateRequest request) {
        validateEventDateTime(request.startDateTime(), request.endDateTime());

        ApiResponse deleteResponse = lessonClient.deleteEventFromLesson(eventClassId);
        if(!deleteResponse.checkStatusOK())
            throw new RuntimeException("[LessonClient] Failed to delete event from lessons. Status: " + deleteResponse.status());

        EventClass eventClass = getEventClassById(eventClassId);
        ApiResponse updateResponse = lessonClient.applyEventToLesson(ApplyEventToLessonRequest.from(
                eventClass.getId(), request.discountRate(),
                request.startDateTime(), request.endDateTime(),
                request.classes()));
        if(!updateResponse.checkStatusOK())
            throw new RuntimeException("[LessonClient] Failed to delete event from lessons. Status: " + deleteResponse.status());

        return eventClass.update(request);
    }

    @Transactional
    public void deleteEventClass(Long eventClassId) {
        EventClass eventClass = getEventClassById(eventClassId);
        ApiResponse response = lessonClient.deleteEventFromLesson(eventClassId);
        if(!response.checkStatusOK())
            throw new RuntimeException("[LessonClient] Failed to delete event from lessons. Status: " + response.status());
        eventClassRepository.delete(eventClass);
    }

    private void validateEventDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!startDateTime.isBefore(endDateTime))
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        if (!startDateTime.isAfter(LocalDateTime.now().plusMinutes(5)))
            throw new IllegalArgumentException("시작 시간은 현재 시간으로부터 최소 5분 이후여야 합니다.");
    }
}
