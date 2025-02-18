package lt.techin.demo.dto;

import lt.techin.demo.model.RunningEvent;

import java.util.List;

public class RunningEventMapper {

  public static RunningEvent toRunningEvent(RunningEventRequestDTO runningEventRequestDTO) {
    return new RunningEvent(
            null,
            runningEventRequestDTO.name(),
            runningEventRequestDTO.calendarDate(),
            runningEventRequestDTO.location(),
            runningEventRequestDTO.maxParticipants(),
            null
    );
  }

  public static RunningEventResponseDTO toRunningEventResponseDTO(RunningEvent runningEvent) {
    return new RunningEventResponseDTO(
            runningEvent.getId(),
            runningEvent.getName(),
            runningEvent.getCalendarDate(),
            runningEvent.getLocation(),
            runningEvent.getMaxParticipants()
    );
  }

  public static List<RunningEventResponseDTO> toRunningEventsResponseDTOList(List<RunningEvent> runningEvents) {
    return runningEvents.stream().map(RunningEventMapper::toRunningEventResponseDTO).toList();
  }
}
