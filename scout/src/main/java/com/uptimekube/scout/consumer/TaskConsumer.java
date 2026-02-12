package com.uptimekube.scout.consumer;

import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.uptimekube.scout.dto.MonitorDTO;

@Component
public class TaskConsumer {

    // Ouve a fila 'monitor-tasks'. O Spring converte o JSON para String automaticamente.
    // (Num passo futuro, converteremos para objeto DTO, por enquanto vamos ver o JSON cru)
    @RabbitListener(queues = "monitor-tasks")
    public void receiveMessage(MonitorDTO task) {
        Instant agora = Instant.now();
        long atrasoEmSegundos = java.time.Duration.between(task.getCreatedAt(), agora).getSeconds();

        System.out.println("👷 [Scout] Processando: " + task.getUrl());
        
        if (atrasoEmSegundos > 5) {
            System.out.println("⚠️ ALERTA: O sistema está lento! Atraso de " + atrasoEmSegundos + " segundos.");
        }

        // Simula um trabalho (dorme 1 segundo)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("✅ [Scout] Tarefa concluída!");
    }
}