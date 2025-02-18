package shop.snowballclass.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.snowballclass.admin.dto.EventCreateRequest;
import shop.snowballclass.admin.dto.EventInfoResponse;
import shop.snowballclass.admin.dto.EventSimpleResponse;
import shop.snowballclass.admin.entity.EventClass;
import shop.snowballclass.admin.repository.EventClassRepository;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventClassService {
    private final EventClassRepository eventClassRepository;
    
    @Transactional
    public EventSimpleResponse createEventClass(EventCreateRequest request) {
        EventClass eventClass = eventClassRepository.save(EventClass.from(request));
        try {
            eventClassRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 이벤트중인 클래스입니다.");
//            if(e.getMessage().contains(request.classes()))
//                throw new IllegalArgumentException("이미 이벤트중인 클래스입니다.");
        }
        return EventSimpleResponse.from(eventClass);
    }

    @Transactional(readOnly = true)
    public EventInfoResponse getEventClassInfo(Long id) {
        return eventClassRepository.findById(id)
                .map(EventInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 이벤트입니다."));
    }

}
