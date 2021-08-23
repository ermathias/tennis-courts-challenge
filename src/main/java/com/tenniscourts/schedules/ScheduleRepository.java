package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByStartDateTimeBeforeAndEndDateTimeAfter(LocalDateTime endDateTime, LocalDateTime startDateTime);

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

}