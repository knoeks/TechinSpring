package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapper {

  public static UserDTO toUserDTO(User user) {
    return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getRoles()
    );
  }

  public static UserPostDTO toUserPostDTO(User user) {
    return new UserPostDTO(
            user.getUsername(),
            user.getRoles()
    );
  }

  public static User toUser(UserDTO userDTO) {
    return new User(
            userDTO.id(),
            userDTO.username(),
            userDTO.password(),
            userDTO.roles()
    );
  }

  public static void updateUser(UserDTO userDTO, User user, PasswordEncoder encoder) {
    user.setUsername(userDTO.username());
    user.setPassword(encoder.encode(userDTO.password()));
    user.setRoles(userDTO.roles());
  }

  public static List<UserDTO> toUserDTOList(List<User> users) {
    return users.stream().map(UserMapper::toUserDTO).toList();
  }
}
