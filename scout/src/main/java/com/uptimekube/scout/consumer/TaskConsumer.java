package com.uptimekube.scout.consumer;

import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uptimekube.scout.dto.MonitorDTO;
import com.uptimekube.scout.service.CheckService;

@Component
public class TaskConsumer {

    @Autowired
    private CheckService checkService;

    // Ouve a fila 'monitor-tasks'. O Spring converte o JSON para String automaticamente.
    @RabbitListener(queues = "monitor-tasks")
    public void receiveMessage(MonitorDTO task) {
        try{
            
            Instant agora = Instant.now();
            long atrasoEmSegundos = java.time.Duration.between(task.getCreatedAt(), agora).getSeconds();

            if (atrasoEmSegundos > 300) {
                //Aqui estamos ignorando as tasks que estao no rabbit a mais de 5min
                System.out.println("[Scout] Ignorando tarefa velha (Atraso: " + atrasoEmSegundos + "s): " + task.getUrl());
                return;
            }

            if (atrasoEmSegundos > 5) {
                System.out.println("⚠️ ALERTA: O sistema está lento! Atraso de " + atrasoEmSegundos + " segundos.");
            }

            //manda o service fazer o
            checkService.performCheck(task);

            System.out.println("[Scout] Processando: " + task.getUrl());

            // Simula um trabalho (dorme 1 segundo)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("✅ [Scout] Tarefa concluída!");
    
        } catch(Exception e) {
            //Bloco de seguranca para que se caso falhar caimos aqui evitando um loop de erro do RabbitMQ
            System.err.println("[ERRO GRAVE] Falha ao processar tarefa" + task.getUrl());
            System.err.println("Motivo: " + e.getMessage());

        }
    
    }
}