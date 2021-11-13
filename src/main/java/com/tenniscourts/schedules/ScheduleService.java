package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;
    //6. As a Tennis Court Admin, I want to be able to create schedule slots for a given tennis court
    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(createScheduleRequestDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<ScheduleDTO> schedules = scheduleMapper.map(scheduleRepository.findByStartDateTimeBetweenEndDateTime(startDate, endDate));
        if(schedules.isEmpty())
            throw new EntityNotFoundException("No Schedules Available");
        return schedules;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            return new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
