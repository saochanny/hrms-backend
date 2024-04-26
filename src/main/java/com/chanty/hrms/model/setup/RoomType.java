package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room_types")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String roomCode;
    private String description;
    private String secDescription;
}
