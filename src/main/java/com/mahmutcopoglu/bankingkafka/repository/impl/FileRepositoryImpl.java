package com.mahmutcopoglu.bankingkafka.repository.impl;

import java.io.IOException;
import java.util.List;

import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.entity.Account;
import com.mahmutcopoglu.bankingkafka.repository.FileRepository;
import com.mahmutcopoglu.bankingkafka.repository.FileProcess;
import org.springframework.stereotype.Component;

@Component
public class FileRepositoryImpl implements FileRepository {

	private FileProcess fileProcess;
	
	public FileRepositoryImpl(FileProcess fileProcess) {
		this.fileProcess = fileProcess;
	}
	@Override
	public void save(Account account) throws IOException {
		this.fileProcess.saveAccount(account);
		
	}

	@Override
	public Account getByAccountNumber(String accountNumber) throws IOException {
		Account account = this.fileProcess.getByAccountNumber(accountNumber);
		return account;
	}

	@Override
	public Account deposit(String accountNumber, double amount) throws IOException {
		Account account = this.fileProcess.addAmount(accountNumber,amount);
		return account;
	}

	@Override
	public Account transferProcess(String accountNumber, String transferredAccountNumber, double amount) throws IOException {
		Account account = this.fileProcess.moneyTransfer(accountNumber,transferredAccountNumber,amount);
		return account;
	}

	@Override
	public List<AccountLogResponseObject> accountLog(String accountNumber) throws IOException {
		return this.fileProcess.logFileReader(accountNumber);
	}

}
