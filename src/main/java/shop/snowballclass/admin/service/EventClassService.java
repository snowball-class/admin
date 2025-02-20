package shop.snowballclass.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.snowballclass.admin.dto.EventCreateRequest;
import shop.snowballclass.admin.dto.EventInfoResponse;
import shop.snowballclass.admin.dto.EventSimpleResponse;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.repository.EventClassRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventClassService {
    private final EventClassRepository eventClassRepository;

    @Transactional
    public EventSimpleResponse createEventClass(EventCreateRequest request) {
        return EventSimpleResponse.from(eventClassRepository.save(EventClass.from(request)));
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long id) {
        return eventClassRepository.findById(id)
                .map(EventInfoResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이벤트입니다."));
    }

}
