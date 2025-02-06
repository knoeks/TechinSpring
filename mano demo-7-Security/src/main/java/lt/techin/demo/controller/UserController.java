package lt.techin.demo.controller;


import lt.techin.demo.model.User;
import lt.techin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @PostMapping("/users")
  public ResponseEntity<User> addUser(@RequestBody User user) {


    // cia reikes panaudoti
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userService.saveUser(user);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri()
            )
            .body(savedUser);

  }
}
