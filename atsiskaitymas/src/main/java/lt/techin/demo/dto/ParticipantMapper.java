package lt.techin.demo.dto;

import lt.techin.demo.model.User;

import java.util.List;

public class ParticipantMapper {

  public static ParticipantDTO toParticipantDTO(User user) {
    return new ParticipantDTO(
            user.getId(),
            user.getUsername()
    );
  }
}
