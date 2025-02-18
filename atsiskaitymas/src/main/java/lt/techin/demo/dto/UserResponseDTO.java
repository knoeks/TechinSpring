package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Role;

import java.util.List;

public record UserResponseDTO(Long id,
                              String username,
                              List<Role> roles) {
}
