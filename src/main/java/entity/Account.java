package entity;

import dto.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

	private int accountNumber;
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
	private Double balance;
	private String lastUpdateDate; 
}
