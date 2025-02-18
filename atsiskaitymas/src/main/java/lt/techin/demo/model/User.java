package lt.techin.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DialectOverride;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;
  private String password;

  // Default yra LAZY
  // Reikia EAGER, nes nespėja Spring Security pasiekti rolių,
  // nes užsidaro session
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  @OneToMany
  @JoinColumn(name = "user_id")
  private List<Registration> registrations;


  public User(Long id, String username, String password, List<Role> roles, List<Registration> registrations) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.registrations = registrations;
  }

  public User() {
  }

  public long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  // Konkrečiai nurodome Spring'ui, jog turime sąrašą rolių
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }
}