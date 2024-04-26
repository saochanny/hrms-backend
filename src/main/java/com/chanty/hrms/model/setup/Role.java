package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private RoleEnum name;

  @Column(name = "description")
  private String description;
}
