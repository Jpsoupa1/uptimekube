package com.uptimekube.commander.scheduler;

import java.time.Instant;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.uptimekube.commander.config.RabbitMQConfig;
import com.uptimekube.commander.dto.MonitorDTO;
import com.uptimekube.commander.model.Monitor;
import com.uptimekube.commander.repository.MonitorRepository;

@Component
public class MonitorScheduler {
    
    @Autowired
    private MonitorRepository monitorRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 10000)
    public void scheduleTasks() {
       
        List<Monitor> monitors = monitorRepository.findAll();
        
        System.out.println("Iniciando agendamento para " + monitors.size() + " monitores");
        
        for (Monitor monitor : monitors) {
            
            if (monitor.isActive()) {
                MonitorDTO task = new MonitorDTO(monitor.getId(), monitor.getUrl(), Instant.now());

                rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, task);

                System.out.println("--> Monitor: " + monitor.getId() + " - " + monitor.getUrl() + " Enviado para a fila");
            }
        }
    }
}
