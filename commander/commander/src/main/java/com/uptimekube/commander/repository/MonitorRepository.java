package com.uptimekube.commander.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uptimekube.commander.model.Monitor;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    
}
