package com.mahmutcopoglu.bankingkafka.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.mahmutcopoglu.bankingkafka.dto.AccountBalanceData;
import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.dto.AccountTransferData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.mahmutcopoglu.bankingkafka.dto.AccountCreateRequest;
import com.mahmutcopoglu.bankingkafka.entity.Account;
import com.mahmutcopoglu.bankingkafka.services.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private KafkaTemplate<String,String> producer;

	@PostMapping("/account")
	public  ResponseEntity<Account> newAccount(@RequestBody AccountCreateRequest accountCreateRequest) throws IOException {
		Account newAccount =  this.accountService.save(accountCreateRequest);
		return ResponseEntity.ok(newAccount);
	}

	@GetMapping(path = "/account/{accountNumber}")
	private ResponseEntity<Account> getAccount(@PathVariable(name = "accountNumber") String accountNumber) throws IOException {
		Account account =  this.accountService.getByAccountNumber(accountNumber);
		return ResponseEntity.ok(account);
	}

	@PatchMapping(path = "/account/{accountNumber}")
	private ResponseEntity<Account> deposit(@PathVariable(name = "accountNumber") String accountNumber,
											@RequestBody AccountBalanceData accountBalanceData) throws IOException {
		Account account =  this.accountService.deposit(accountNumber, accountBalanceData);
		String message = accountNumber + " deposit amount:" + accountBalanceData.getAmount();
		producer.send("logs",message);
		return ResponseEntity.ok(account);
	}

	@PutMapping(path = "/account/transfer/{transferAccountNumber}")
	private ResponseEntity<Account> transferAmount(@PathVariable(name = "transferAccountNumber") String transferAccountNumber,
												   @RequestBody AccountTransferData accountTransferData) throws IOException {
		Account account =  this.accountService.transferProcess(transferAccountNumber, accountTransferData);
		String message = transferAccountNumber + " transfer amount:" + accountTransferData.getAmount()+ ",transferred_account:" + accountTransferData.getTransferredAccountNumber();
		producer.send("logs",message);
		return ResponseEntity.ok(account);
	}

	@GetMapping(path = "/account/log/{accountNumber}")
	private List<AccountLogResponseObject> getAccountLogs(@PathVariable(name = "accountNumber") String accountNumber) throws IOException {
		return this.accountService.accountLog(accountNumber);
	}

	
}
