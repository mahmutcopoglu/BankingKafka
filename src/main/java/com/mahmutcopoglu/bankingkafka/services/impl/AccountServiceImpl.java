package com.mahmutcopoglu.bankingkafka.services.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mahmutcopoglu.bankingkafka.dto.AccountBalanceData;
import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.dto.AccountTransferData;
import com.mahmutcopoglu.bankingkafka.helper.DateHelper;
import com.mahmutcopoglu.bankingkafka.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahmutcopoglu.bankingkafka.dto.AccountCreateRequest;
import com.mahmutcopoglu.bankingkafka.entity.Account;
import com.mahmutcopoglu.bankingkafka.repository.FileRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private DateHelper dateHelper;
	
	private String createAccountNumber() {
		Random rnd = new Random();
		int accountNumber = rnd.nextInt(100000000, 999999999);
		return String.valueOf(accountNumber);
	}

	@Override
	public Account save(AccountCreateRequest accountCreateRequest) throws IOException {
		Account account = accountRequestMapper(accountCreateRequest);
		this.fileRepository.save(account);
		return account;
	}

	@Override
	public Account getByAccountNumber(String accountNumber) throws IOException {
		Account account = this.fileRepository.getByAccountNumber(accountNumber);
		return account;
	}

	@Override
	public Account deposit(String accountNumber, AccountBalanceData accountBalanceData) throws IOException {
		Account account = this.fileRepository.deposit(accountNumber, accountBalanceData.getAmount());
		return account;
	}

	@Override
	public Account transferProcess(String accountNumber, AccountTransferData accountTransferData) throws IOException{
		Account account = this.fileRepository.transferProcess(accountNumber, accountTransferData.getTransferredAccountNumber(), accountTransferData.getAmount());
		return account;
	}

	@Override
	public List<AccountLogResponseObject> accountLog(String accountNumber) throws IOException {
		return this.fileRepository.accountLog(accountNumber);
	}

	private Account accountRequestMapper(AccountCreateRequest accountCreateRequest) throws IOException {

		Account account = Account.builder().accountNumber(createAccountNumber())
				.name(accountCreateRequest.getName()).surname(accountCreateRequest.getSurname())
				.email(accountCreateRequest.getEmail()).tc(accountCreateRequest.getTc())
				.balance(0.0).type(accountCreateRequest.getType()).lastUpdateDate(this.dateHelper.formatDate(LocalDateTime.now())).build();
		return account;

	}

	
}
