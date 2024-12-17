package com.example.Homework10.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    private long senderAccountId;
    private long receiverAccountId;
    private BigDecimal amount;
}
