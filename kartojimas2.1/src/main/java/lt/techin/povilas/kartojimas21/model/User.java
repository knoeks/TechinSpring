package lt.techin.povilas.kartojimas21.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 5, max = 50)
  private String username;
  @NotBlank
  private String password;

  @ManyToMany
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

  @OneToMany
  @JoinColumn(name = "user_id")
  private List<Rental> rentals;

  public User(Long id, String username, String password, List<Role> roles, List<Rental> rentals) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.rentals = rentals;
  }

  public User() {
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

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
}
