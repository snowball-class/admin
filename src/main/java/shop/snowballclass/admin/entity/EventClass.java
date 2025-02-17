package shop.snowballclass.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import shop.snowballclass.admin.dto.EventCreateRequest;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class EventClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String issuer;
    @Column(nullable = false, length = 50)
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDateTime;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static EventClass from(EventCreateRequest request) {
        return EventClass.builder()
                .issuer(request.issuer())
                .title(request.title())
                .startDateTime(request.startDateTime())
                .endDateTime(request.endDateTime())
                .build();
    }

    @Builder
    private EventClass(String issuer, String title, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.issuer = issuer;
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void update() {

    }
}
