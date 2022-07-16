package com.mahmutcopoglu.bankingkafka.dto;

import lombok.Data;

@Data
public class AccountTransferData {
    private String transferredAccountNumber;
    private double amount;
}
