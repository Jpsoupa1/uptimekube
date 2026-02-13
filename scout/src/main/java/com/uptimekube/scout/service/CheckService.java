package com.uptimekube.scout.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uptimekube.scout.dto.MonitorDTO;

@Service
public class CheckService {
    
    private final WebClient webClient;
    
    public CheckService(WebClient.Builder webClientBuilder) {
        //Configura o navegador padrao
        this.webClient = webClientBuilder.build();
    }

    public void performCheck(MonitorDTO monitor) {
        String url = monitor.getUrl();
        long startTime = System.currentTimeMillis();

        try {
            
            webClient.get()
                     .uri(url)
                     .retrieve()
                     .toBodilessEntity()
                     .block(Duration.ofSeconds(10));
            
            long endTime = System.currentTimeMillis();
            long latency = endTime -startTime; 

            System.out.println("✅ [SUCESSO] Site: " + url + " | Status: 200 OK | Tempo: " + latency + "ms");

        } catch (WebClientResponseException e) {

            long latency = System.currentTimeMillis() - startTime;

            System.out.println("🔥 [FALHA CRÍTICA] Site: " + url + " | Erro: " + e.getMessage());

        }
    }

}
