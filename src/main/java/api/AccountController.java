package api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dto.AccountCreateRequest;
import entity.Account;
import services.AccountService;

@RestController
public class AccountController {
	
	private AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/account")
	public  ResponseEntity<Account> newAccount(@RequestBody AccountCreateRequest accountCreateRequest) throws IOException {
		Account newAccount =  this.accountService.save(accountCreateRequest);
		return ResponseEntity.ok(newAccount);
	}

	
}
