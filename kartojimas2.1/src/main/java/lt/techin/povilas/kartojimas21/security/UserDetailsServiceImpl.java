package lt.techin.povilas.kartojimas21.security;

import lt.techin.povilas.kartojimas21.model.User;
import lt.techin.povilas.kartojimas21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Autowired

  public UserDetailsServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUser = userService.findUserByUsername(username);

    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException(username);
    }

    return optionalUser.get();
  }
}
