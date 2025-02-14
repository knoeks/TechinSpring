package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Role;

import java.util.List;

public record UserPostDTO(String username,
                          List<Role> roles) {
}
