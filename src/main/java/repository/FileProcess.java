package repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import entity.Account;


public class FileProcess {
	private File file = new File("accounts.txt");
	
	public FileProcess() {
        this.init();
    }
	
	public void init() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public Boolean newAccount(Account account) throws IOException {

		String fileLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", account.getAccountNumber(), account.getName(),
				account.getSurname(), account.getEmail(), account.getTc(), account.getType(),
				account.getBalance(), account.getLastUpdateDate());
        FileWriter writter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writter);
        try {
            bufferedWriter.write(fileLine);
            bufferedWriter.newLine();
           
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            bufferedWriter.close();
            
        }

        return true;
    }
	
	

}
