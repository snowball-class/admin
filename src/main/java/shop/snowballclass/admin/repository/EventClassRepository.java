package shop.snowballclass.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.snowballclass.admin.entity.EventClass;

import java.time.LocalDateTime;
import java.util.List;

public interface EventClassRepository extends JpaRepository<EventClass,Long> {

    @Query("SELECT e FROM EventClass e WHERE e.id IN (:eventIds) AND " +
            "NOT (e.endDateTime < :start OR e.startDateTime > :end)")
    List<EventClass> findEventsByTimeRangeAndIds(
            @Param("eventIds") List<Long> eventIds,
            @Param("startTime") LocalDateTime start,
            @Param("endTime") LocalDateTime end);
}
