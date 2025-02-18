package lt.techin.demo.controller;

import jakarta.validation.Valid;
import lt.techin.demo.dto.*;
import lt.techin.demo.model.Registration;
import lt.techin.demo.model.RunningEvent;
import lt.techin.demo.model.User;
import lt.techin.demo.service.RegistrationService;
import lt.techin.demo.service.RunningEventService;
import lt.techin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RunningEventController {
  private final RegistrationService registrationService;
  private final RunningEventService runningEventService;
  private final UserService userService;

  @Autowired
  public RunningEventController(RegistrationService registrationService, RunningEventService runningEventService, UserService userService) {
    this.registrationService = registrationService;
    this.runningEventService = runningEventService;
    this.userService = userService;
  }

  @PostMapping("/events")
  public ResponseEntity<RunningEventResponseDTO> addEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO) {

    //wtf???
    RunningEvent runningEvent = runningEventService.saveRunningEvent(RunningEventMapper.toRunningEvent(runningEventRequestDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(runningEvent.getId())
                    .toUri()
    ).body(RunningEventMapper.toRunningEventResponseDTO(runningEvent));
  }

  @DeleteMapping("/events/{eventId}")
  public ResponseEntity<Void> addEvent(@PathVariable Long eventId) {
    Optional<RunningEvent> optionalRunningEvent = runningEventService.findRunningEventById(eventId);

    if (optionalRunningEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    runningEventService.deleteRunningEventById(eventId);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/events")
  public ResponseEntity<List<RunningEventResponseDTO>> getRunningEvents() {
    return ResponseEntity.ok(RunningEventMapper.toRunningEventsResponseDTOList(runningEventService.findAllRunningEvents()));
  }

  @PostMapping("/events/{eventId}/register")
  public ResponseEntity<?> addRegistration(@PathVariable Long eventId, @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO, Authentication authentication) {
    Optional<RunningEvent> optionalRunningEvent = runningEventService.findRunningEventById(eventId);
    // If eventId cannot be found, don’t forget to return 404 Not Found
    if (optionalRunningEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<Registration> registrations = optionalRunningEvent.get().getRegistrations();

    Registration registration = RegistrationMapper.toRegistration(registrationRequestDTO, optionalRunningEvent.get());

    if (registrations.stream().anyMatch(item -> registrationRequestDTO.user().getId() == item.getUser().getId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Same user cannot register on the same running event twice.");
    }

    Optional<User> optionalUser = userService.findUserById(registrationRequestDTO.user().getId());

    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (!optionalUser.get().getUsername().equals(authentication.getName())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user can only register himself on events.");
    }

    // po visu validaciju
    Registration savedRegistration = registrationService.saveRegistration(registration);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRegistration.getId())
                    .toUri()
    ).body(RegistrationMapper.toRegistrationResponseDTO(savedRegistration));
  }

  @GetMapping("/events/{eventId}/participants")
  public ResponseEntity<List<ParticipantDTO>> getParticipants(@PathVariable Long eventId) {
    List<Registration> registrations = registrationService.findRegistrationsByEventId(eventId);

    if (registrations.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(registrations.stream().map(item -> ParticipantMapper.toParticipantDTO(item.getUser())).toList());
  }
}
