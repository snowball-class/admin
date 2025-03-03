package shop.snowballclass.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.snowballclass.admin.dto.event.EventCreateRequest;
import shop.snowballclass.admin.dto.event.EventInfoResponse;
import shop.snowballclass.admin.dto.event.EventUpdateRequest;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.repository.EventClassRepository;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventClassService {
    private final EventClassRepository eventClassRepository;

    @Transactional(readOnly = true)
    public EventClass getEventClassById(Long id) {
        return eventClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이벤트입니다."));
    }

    @Transactional
    public Long createEventClass(EventCreateRequest request) {
        validateEventDateTime(request.startDateTime(), request.endDateTime());
        EventClass eventClass = EventClass.from(request);
        try {
            eventClassRepository.saveAndFlush(eventClass);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("입력값이 잘못되었습니다: " + e.getMessage());
        }
        return eventClass.getId();
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long eventClassId) {
        return EventInfoResponse.from(getEventClassById(eventClassId));
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
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        if (!startDateTime.isAfter(LocalDateTime.now().plusMinutes(5)))
            throw new IllegalArgumentException("시작 시간은 현재 시간으로부터 최소 5분 이후여야 합니다.");
    }
}
