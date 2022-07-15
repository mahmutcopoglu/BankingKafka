package repository;

import java.io.IOException;

import entity.Account;

public interface FileRepository {

	void save(Account account) throws IOException;
}
