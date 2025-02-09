package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Role;

import java.util.List;

public record UserDTO(Long id,
                      String username,
                      String password,
                      List<Role> roles) {
}
