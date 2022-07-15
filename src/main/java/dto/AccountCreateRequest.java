package dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountCreateRequest {
	
	private String name;
	private String surname;
	private String email;
	private String tc;
	private AccountType type;
}
