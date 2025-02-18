package lt.techin.demo.dto;

import lt.techin.demo.model.User;

public class UserMapper {
  public static User toUser(UserRequestDTO userRequestDTO) {
    return new User(
            null,
            userRequestDTO.username(),
            userRequestDTO.password(),
            userRequestDTO.roles(),
            null
    );
  }

  public static UserResponseDTO toUserResponseDTO(User user) {
    return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getRoles()
    );
  }


}
