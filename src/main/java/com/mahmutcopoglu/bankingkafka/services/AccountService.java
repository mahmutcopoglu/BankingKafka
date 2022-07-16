package com.mahmutcopoglu.bankingkafka.services;

import java.io.IOException;
import java.util.List;

import com.mahmutcopoglu.bankingkafka.dto.AccountBalanceData;
import com.mahmutcopoglu.bankingkafka.dto.AccountCreateRequest;
import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.dto.AccountTransferData;
import com.mahmutcopoglu.bankingkafka.entity.Account;

public interface AccountService {

	Account save(AccountCreateRequest accountCreateRequest) throws IOException;

	Account getByAccountNumber(String accountNumber) throws IOException;

	Account deposit (String accountNumber, AccountBalanceData accountBalanceData) throws IOException;

	Account transferProcess(String accountNumber, AccountTransferData accountTransferData) throws IOException;

	List<AccountLogResponseObject> accountLog(String accountNumber) throws IOException;
}
