package com.mahmutcopoglu.bankingkafka.dto;

import lombok.Data;

@Data
public class AccountCreateRequest {
	
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
}
