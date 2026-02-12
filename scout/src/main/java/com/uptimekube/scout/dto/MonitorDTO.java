package com.uptimekube.scout.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorDTO {
    private Long monitorId;
    private String url;
    private Instant createdAt;
}
