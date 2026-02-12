package com.uptimekube.commander.controller;

import com.uptimekube.commander.model.Monitor;
import com.uptimekube.commander.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitors")
public class MonitorController {
    @Autowired
    private MonitorRepository monitorRepository;


    @PostMapping
    public Monitor create(@RequestBody Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    @GetMapping
    public List<Monitor> listAll() {
        return monitorRepository.findAll();
    }
}
