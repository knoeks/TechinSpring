package lt.techin.movie_studio.controller;

import jakarta.validation.Valid;
import lt.techin.movie_studio.dto.UserDTO;
import lt.techin.movie_studio.dto.UserMapper;
import lt.techin.movie_studio.dto.UserPostDTO;
import lt.techin.movie_studio.model.User;
import lt.techin.movie_studio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    //reiks cia prideti password encoderi
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getUsers() {
    return ResponseEntity.ok(UserMapper.toUserDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id, Authentication authentication) {

    Optional<User> optionalUser = userService.findUserById(id);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals("ROLE_ADMIN"));
    boolean isSame = userService.findUserById(id).get().getUsername().equals(authentication.getName());

    if (!isAdmin && !isSame) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(UserMapper.toUserDTO(optionalUser.get()));
  }

  @PostMapping("/users")
  public ResponseEntity<UserPostDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {

    User user = UserMapper.toUser(userDTO);
    // cia reiks pakeisti pasword i kita
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri()
            )
            .body(UserMapper.toUserPostDTO(savedUser));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<UserDTO> saveUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, Authentication authentication) {
    // reiks validation dar pasidaryt kad cia jau pareitu
    // tikslingi duomenys

    Optional<User> optionalUser = userService.findUserById(id);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals("ROLE_ADMIN"));
    boolean isSame = userService.findUserById(id).get().getUsername().equals(authentication.getName());

    if (!isAdmin && !isSame) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    User userFromDB = optionalUser.get();

    UserMapper.updateUser(userDTO, userFromDB, passwordEncoder);

    userService.saveUser(userFromDB);

    return ResponseEntity.ok(UserMapper.toUserDTO(userFromDB));
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
    if (userService.findUserById(id).isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals("ROLE_ADMIN"));
    boolean isSame = userService.findUserById(id).get().getUsername().equals(authentication.getName());

    if (!isAdmin && !isSame) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}
