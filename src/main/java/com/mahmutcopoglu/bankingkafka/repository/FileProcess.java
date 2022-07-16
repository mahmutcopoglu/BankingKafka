package com.mahmutcopoglu.bankingkafka.repository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.mahmutcopoglu.bankingkafka.dto.AccountLogResponseObject;
import com.mahmutcopoglu.bankingkafka.dto.AccountType;
import com.mahmutcopoglu.bankingkafka.entity.Account;
import com.mahmutcopoglu.bankingkafka.helper.DateHelper;
import com.mahmutcopoglu.bankingkafka.services.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

@Component
public class FileProcess {
	private File file = new File("accounts.txt");
    private File logFile = new File("logs.txt");


    @Autowired
	public FileProcess() {
        this.init();
    }
    @Autowired
    public DateHelper dateHelper;
	
	public void init() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	
	public Boolean saveAccount(Account account) throws IOException {
        String fileLine;
        var lineAccount = fileInTheAccount(account.getAccountNumber());
        if(lineAccount !=null){
            fileLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s", account.getAccountNumber(), account.getName(),
                            account.getSurname(), account.getEmail(), account.getTc(), account.getType(),
                            account.getBalance(), account.getLastUpdateDate());
            this.fileInTheAccountUpdate(fileLine,account.getAccountNumber());
        }else{
            fileLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s", account.getAccountNumber(), account.getName(),
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
        }



        return true;
    }

    public Account getByAccountNumber(String accountNumber) throws  IOException {
        var accNumber = fileInTheAccount(accountNumber);
        if (accNumber != null) {
            return mapperForAccount(accNumber);
        }
        return null;
    }

    public Account moneyTransfer(String accountNumber,String transferAccountNumber,double amount) throws  IOException{
        Account account=this.getByAccountNumber(accountNumber);
        Account transferAccount = this.getByAccountNumber(transferAccountNumber);
        account.setBalance(account.getBalance() - amount);
        transferAccount.setBalance(transferAccount.getBalance() + amount);
        this.saveAccount(account);
        this.saveAccount(transferAccount);
        return account;
    }

    private String fileInTheAccount(String accountNumber) throws IOException {
        FileReader fileReader = new FileReader(file);
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(accountNumber + ",")) {
                    return line;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return line;
    }



    public Account mapperForAccount(String text) {
        String[] textSplit = text.split(",");
        return Account.builder().accountNumber((textSplit[0])).name(textSplit[1]).surname(textSplit[2])
                .email(textSplit[3]).tc(textSplit[4]).type(AccountType.valueOf(textSplit[5]))
                .balance(Double.parseDouble(textSplit[6])).lastUpdateDate(textSplit[7]).build();
    }

    public Account addAmount(String accountNumber, double amount) throws  IOException{
        Account account = this.getByAccountNumber(accountNumber);
        account.setBalance(account.getBalance() + amount);
        account.setLastUpdateDate(this.dateHelper.formatDate(LocalDateTime.now()));
        this.saveAccount(account);
        return account;
    }

    private void fileInTheAccountUpdate(String accountLine, String accountNumber) throws IOException {
        FileReader fileReader = new FileReader(file);
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer inputBuffer = new StringBuffer();

        try {

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(accountNumber + ",")) {
                    inputBuffer.append(accountLine);

                }
                else{
                    inputBuffer.append(line);
                }
                inputBuffer.append("\n");
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(inputBuffer.toString().getBytes());

            fileOut.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            bufferedReader.close();
        }

    }

    public Boolean saveLog(String logLine) throws IOException {
        FileWriter writter = new FileWriter(logFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writter);
        try {
            bufferedWriter.write(logLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            bufferedWriter.close();

        }
        return true;
    }

    public List<AccountLogResponseObject> logFileReader(String accountNumber) throws IOException {
        ArrayList<AccountLogResponseObject> logList = new ArrayList<AccountLogResponseObject>();
        Account account=this.getByAccountNumber(accountNumber);
        FileReader fileReader = new FileReader(logFile);
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(accountNumber + " ")) {

                    var logLine="";
                    if(line.contains(" "+"deposit"+" ")){
                        logLine=account.getAccountNumber()+" nolu hesaba "+ line.split("amount:")[1] +" "+ account.getType() + " yatırılmıştır.";

                    }else if(line.contains(" "+"transfer"+" ")){
                        logLine=account.getAccountNumber()+" hesaptan "+ line.split("transferred_account:")[1] +" hesaba "+((line.split(",")[0]).split("amount:")[1])+ " " + account.getType() + " yatırılmıştır.";
                    }
                    logList.add(AccountLogResponseObject.builder().log(logLine).build());

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            bufferedReader.close();
        }
        return logList;
    }



	
	

}
