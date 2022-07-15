package services.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dto.AccountCreateRequest;
import entity.Account;
import repository.FileRepository;
import services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private FileRepository fileRepository;
	
	@Autowired
	public AccountServiceImpl(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}
	
	private int createAccountNumber() {
		Random rnd = new Random();
		int accountNumber = rnd.nextInt(100000000, 999999999);
		return accountNumber;
	}
	
	public String getCurrentTimeUsingDate() {
	    Date date = new Date();
	    String strDateFormat = "hh:mm:ss a";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate = dateFormat.format(date);
	    return formattedDate;
	}
	
	@Override
	public Account save(AccountCreateRequest accountCreateRequest) throws IOException {
		Account account = accountRequestMapper(accountCreateRequest);
		this.fileRepository.save(account);
		return account;
	}
	
	private Account accountRequestMapper(AccountCreateRequest accountCreateRequest) throws IOException {

		Account account = Account.builder().accountNumber(createAccountNumber())
				.name(accountCreateRequest.getName()).surname(accountCreateRequest.getSurname())
				.email(accountCreateRequest.getEmail()).tc(accountCreateRequest.getTc())
				.balance(0.0).type(accountCreateRequest.getType()).lastUpdateDate(getCurrentTimeUsingDate()).build();
		return account;

	}

	
}
