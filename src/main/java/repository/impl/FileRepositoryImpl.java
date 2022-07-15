package repository.impl;

import java.io.IOException;

import entity.Account;
import repository.FileProcess;
import repository.FileRepository;

public class FileRepositoryImpl implements FileRepository {

	private FileProcess fileProcess;
	
	public FileRepositoryImpl(FileProcess fileProcess) {
		this.fileProcess = fileProcess;
	}
	@Override
	public void save(Account account) throws IOException {
		this.fileProcess.newAccount(account);
		
	}

}
