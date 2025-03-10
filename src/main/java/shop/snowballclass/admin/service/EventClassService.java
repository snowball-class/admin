package shop.snowballclass.admin.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.snowballclass.admin.client.ViewClient;
import shop.snowballclass.admin.dto.client.EventLessonCreateRequest;
import shop.snowballclass.admin.dto.client.EventLessonResponse;
import shop.snowballclass.admin.dto.event.EventCreateRequest;
import shop.snowballclass.admin.dto.event.EventInfoResponse;
import shop.snowballclass.admin.dto.event.EventUpdateRequest;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.exception.ErrorCode;
import shop.snowballclass.admin.exception.common.EntityNotFoundException;
import shop.snowballclass.admin.exception.common.ExternalServiceException;
import shop.snowballclass.admin.repository.EventClassRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventClassService {
    private final EventClassRepository eventClassRepository;
    private final ViewClient viewClient;

    @Transactional(readOnly = true)
    public EventClass getEventClassById(Long id) {
        return eventClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EVENT_NOT_FOUND, "존재하지 않는 이벤트입니다."));
    }

    @Transactional
    public Long createEventClass(EventCreateRequest request) {
        validateEventDateTime(request.startDateTime(), request.endDateTime());
        try {
            String lessonIds = request.lessonIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            List<EventLessonResponse> eventLessonList = viewClient.getEventLessonListByLessonIds(lessonIds).data();
            List<Long> eventIdList = eventLessonList.stream()
                    .map(EventLessonResponse::eventId)
                    .distinct()
                    .collect(Collectors.toList());

            // 이벤트 기간이 겹치는 이벤트 ID 목록 가져오기
            List<EventClass> overlappingEvents = eventClassRepository.findEventsByTimeRangeAndIds(eventIdList, request.startDateTime(), request.endDateTime());
            if (!overlappingEvents.isEmpty()) {
                List<Long> overlappedLessonIds = eventLessonList.stream()
                        .filter(response -> overlappingEvents.stream()
                                .anyMatch(event -> event.getId().equals(response.eventId())))
                        .map(EventLessonResponse::lessonId)
                        .distinct()
                        .collect(Collectors.toList());
                throw new IllegalArgumentException(String.format("다른 이벤트와 기간이 겹치는 클래스가 존재합니다. [Overlapped Lesson Ids : %s]", overlappedLessonIds));
            }

            EventClass eventClass = EventClass.from(request);
            eventClassRepository.saveAndFlush(eventClass);
            viewClient.createEventLessons(eventClass.getId(), EventLessonCreateRequest.from(request.lessonIds()));
            return eventClass.getId();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("입력값이 잘못되었습니다: " + e.getMessage());
        } catch (FeignException e) {
            throw new ExternalServiceException(String.format("Failed to get response on createEventClass. [status:%s][message:%s]", e.status(), e.getMessage()));
        }
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long eventClassId) {
        return EventInfoResponse.from(getEventClassById(eventClassId));
    }

    @Transactional(readOnly = true)
    public List<EventInfoResponse> getAllEventClassInfo() {
        return eventClassRepository.findAll().stream()
                .map(EventInfoResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateEventClass(Long eventClassId, EventUpdateRequest request) {
        validateEventDateTime(request.startDateTime(), request.endDateTime());
        return getEventClassById(eventClassId).update(request);
    }

    @Transactional
    public void deleteEventClass(Long eventClassId) {
        EventClass eventClass = getEventClassById(eventClassId);
        eventClassRepository.delete(eventClass);
    }

    private void validateEventDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!startDateTime.isBefore(endDateTime))
            throw new IllegalArgumentException("시작 시간은 종료 시간 이전여야 합니다.");
        if (!endDateTime.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("종료 시간은 현재 시간 이후여야 합니다.");
    }
}
