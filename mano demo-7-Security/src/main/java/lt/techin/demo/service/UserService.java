package lt.techin.demo.service;

import lt.techin.demo.model.User;
import lt.techin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User saveUser(User user) {
    return userRepository.save(user);
  }

}
