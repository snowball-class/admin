package shop.snowballclass.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.snowballclass.admin.client.LessonClient;
import shop.snowballclass.admin.dto.event.EventCreateRequest;
import shop.snowballclass.admin.dto.event.EventInfoResponse;
import shop.snowballclass.admin.dto.event.EventSimpleResponse;
import shop.snowballclass.admin.dto.event.EventUpdateRequest;
import shop.snowballclass.admin.dto.lesson.LessonResponse;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.repository.EventClassRepository;

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
        return EventSimpleResponse.from(eventClassRepository.save(EventClass.from(request)));
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long eventClassId) {
        EventClass eventInfo = getEventClassById(eventClassId);
        List<LessonResponse> classes = lessonClient.getEventLessonList(eventClassId).data();
        return EventInfoResponse.from(eventInfo, classes);
    }

    @Transactional
    public Long updateEventClass(Long eventClassId, EventUpdateRequest request) {
        return getEventClassById(eventClassId).update();
    }

    @Transactional
    public void deleteEventClass(Long eventClassId) {
        eventClassRepository.delete(getEventClassById(eventClassId));
    }

}
