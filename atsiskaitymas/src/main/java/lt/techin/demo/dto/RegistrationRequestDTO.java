package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.demo.model.User;

public record RegistrationRequestDTO(@NotNull
                                     User user) {
}
