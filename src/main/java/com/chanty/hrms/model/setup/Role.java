package com.chanty.hrms.model.setup;

import com.chanty.hrms.common.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private RoleEnum name;

  @Column(name = "description")
  private String description;
  @ManyToMany(fetch = FetchType.EAGER, targetEntity = Permission.class , cascade = CascadeType.ALL)
  private Set<Permission> permissions;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
}
