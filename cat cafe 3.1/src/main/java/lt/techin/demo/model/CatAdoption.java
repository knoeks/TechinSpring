package lt.techin.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.security.Timestamp;

@Entity
@Table(name = "reservations")

public class CatAdoption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "user_id")
  private Long userId;

  @NotNull
  @Size(max = 50)
  @Column(name = "cat_name")
  private String catName;

  @NotNull
  @Size(max = 50)
  private String status;

  @NotNull
  private Timestamp application_date;
}
