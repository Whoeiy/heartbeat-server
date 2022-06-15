package com.example.heartbeatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerToken {
    private Integer customerId;
    private String token;
}
