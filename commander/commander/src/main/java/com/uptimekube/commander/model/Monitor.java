package com.uptimekube.commander.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "monitors")
public class Monitor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    private Integer intervalSeconds = 60;

    private boolean active= true;

    private LocalDateTime lastChecked;

    private Integer lastStatus;  // 200 (OK), 404 (Erro), 500 (Caiu)

}
