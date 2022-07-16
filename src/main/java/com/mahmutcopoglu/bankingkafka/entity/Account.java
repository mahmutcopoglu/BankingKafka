package com.mahmutcopoglu.bankingkafka.entity;

import com.mahmutcopoglu.bankingkafka.dto.AccountType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Account {

	private String accountNumber;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	private Double balance;
	private String lastUpdateDate;
}
