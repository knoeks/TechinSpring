package lt.techin.demo.controller;

import jakarta.validation.Valid;
import lt.techin.demo.dto.UserMapper;
import lt.techin.demo.dto.UserRequestDTO;
import lt.techin.demo.dto.UserResponseDTO;
import lt.techin.demo.model.User;
import lt.techin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  // Bean'as egzistuojas kontekste, dėl to galime
  // ir @Autowire
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/auth/register")
  // Kai kursite patys, būtinai naudokite DTO!
  public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

    User user = UserMapper.toUser(userRequestDTO);
    // Slatpažodis yra šifruojamas prieš saugant į duomenų bazę
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(UserMapper.toUserResponseDTO(savedUser));
  }
}
