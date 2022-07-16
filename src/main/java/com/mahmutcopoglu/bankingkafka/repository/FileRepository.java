package com.mahmutcopoglu.bankingkafka.repository;

import java.io.IOException;
import java.util.List;

import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.entity.Account;

public interface FileRepository {

	void save(Account account) throws IOException;

	Account getByAccountNumber(String accountNumber) throws IOException;

	Account deposit(String accountNumber, double amount) throws IOException;

	Account transferProcess(String accountNumber, String transferredAccountNumber, double amount) throws IOException;

	List<AccountLogResponseObject> accountLog(String accountNumber) throws IOException;
}
