package lt.techin.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Role;

import java.util.List;

public record UserRequestDTO(@NotNull
                             @Pattern(regexp = "^[a-z0-9]{4,100}$", message = "username must be lowercase letters or numbers, 4-50 characters long.")
                             String username,
                             @NotNull
                             @Size(min = 6, max = 150)
                             String password,
                             @NotNull
                             List<Role> roles) {
}
