package services;

import java.io.IOException;

import dto.AccountCreateRequest;
import entity.Account;

public interface AccountService {

	Account save(AccountCreateRequest accountCreateRequest) throws IOException;
}
