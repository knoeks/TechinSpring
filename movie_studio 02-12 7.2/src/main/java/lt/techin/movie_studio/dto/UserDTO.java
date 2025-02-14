package lt.techin.movie_studio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lt.techin.movie_studio.model.Role;

import java.util.List;

public record UserDTO(Long id,
                      @NotNull
                      @Size(min = 5, max = 50)
                      String username,
                      @NotNull
                      @Size(min = 5, max = 100)
                      String password,
                      List<Role> roles) {
}
