package shop.snowballclass.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import shop.snowballclass.admin.dto.event.EventCreateRequest;

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
    @Column(nullable = false)
    private Integer discountRate;
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
                .discountRate(request.discountRate())
                .startDateTime(request.startDateTime())
                .endDateTime(request.endDateTime())
                .build();
    }

    @Builder
    private EventClass(String issuer, String title, Integer discountRate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.issuer = issuer;
        this.title = title;
        this.discountRate = discountRate;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long update() {
        return id;
    }
}
