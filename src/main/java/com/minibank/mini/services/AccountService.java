package com.minibank.mini.services;

import com.minibank.mini.dto.AccountDto;
import com.minibank.mini.dto.AdditionalInfo;
import com.minibank.mini.dto.Response;
import com.minibank.mini.enums.TransactionType;
import com.minibank.mini.misc.Utils;
import com.minibank.mini.models.Account;
import com.minibank.mini.models.Transaction;
import com.minibank.mini.repository.AccountRepository;
import com.minibank.mini.repository.TransactionRepository;
import com.minibank.mini.services.interfaces.AccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Slf4j
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Response createAccount(AccountDto accountDto) {

        Response response = null;

        try {
            //save basic data and return the id
            Account account = new Account();
            account.setBalance(0.0);
            account.setName(accountDto.getName());
            account.setPhoneNumber(accountDto.getPhoneNumber());
            account.setCreatedAt(new Date());

            accountRepository.save(account);

            String accountNumber = Utils.generateAccountNumber(account.getId());

            account.setAccountNumber(accountNumber);

            accountRepository.save(account);

            List<AdditionalInfo> additionalInfos = new ArrayList<>();
            additionalInfos.add(Utils.getAdditionalInfo("account Number", accountNumber));

            response = new Response("0", "Success", additionalInfos);

        } catch (Exception e) {
            response = new Response("1", "Failed due to certain issue");
        }

        return response;
    }

    @Override
    @Transactional
    public Response deposit(String toAccount, Double amount) {
        Response response = null;
        try {

            if (amount <= 0){
                return new Response("1", "Amount provided is invalid, please provide a valid amount");
            }

            //get the account
            Optional<Account> optionalAccount = accountRepository.findByAccountNumber(toAccount);

            if (optionalAccount.isPresent()){

                Account account = optionalAccount.get();
                //make sure you validate the amount
                //increment the balance and update the account balance
                double newBalance = account.getBalance() + amount;

                account.setBalance(newBalance);

                accountRepository.save(account);

                //and save the transaction
                Transaction transaction = new Transaction();
                transaction.setAccount(account);
                transaction.setCreatedAt(new Date());
                transaction.setFromAccount(account.getAccountNumber());
                transaction.setToAccount(account.getAccountNumber());
                transaction.setDescription("DEPOSIT");
                transaction.setType(TransactionType.DEPOSIT);
                transaction.setAmount(amount);

                transactionRepository.save(transaction);

                List<AdditionalInfo> additionalInfos = new ArrayList<>();
                additionalInfos.add(Utils.getAdditionalInfo("Deposited Amount", String.valueOf(amount)));
                additionalInfos.add(Utils.getAdditionalInfo("Account Balance", String.valueOf(newBalance)));

                response = new Response("0", "Success", additionalInfos);

            } else {
                response = new Response("1", "Account Does not exist");
            }

        } catch (Exception e) {
            response = new Response("1", "Could not complete deposit transaction");
        }

        //return success or failure
        return response;
    }

    @Override
    public Response withdrawal(String fromAccount, Double amount) {

        Response response = null;

        //validate the amount

        try {

            if (amount <= 0){
                return new Response("1", "Amount provided is invalid, please provide a valid amount");
            }

            //fetch account and debit the amount from the account balance
            Optional<Account> optionalAccount = accountRepository.findByAccountNumber(fromAccount);

            if (optionalAccount.isPresent()){
                Account account = optionalAccount.get();

                if (amount > account.getBalance()){
                    return new Response("1", "Your account balance is too small for the withdrawal amount");
                }

                double newBalance = account.getBalance() - amount;

                account.setBalance(newBalance);

                accountRepository.save(account);

                //and save the transaction
                Transaction transaction = new Transaction();
                transaction.setAccount(account);
                transaction.setCreatedAt(new Date());
                transaction.setFromAccount(account.getAccountNumber());
                transaction.setToAccount(account.getAccountNumber());
                transaction.setDescription("WITHDRAWAL");
                transaction.setType(TransactionType.WITHDRAWAL);
                transaction.setAmount(amount);

                transactionRepository.save(transaction);

                List<AdditionalInfo> additionalInfos = new ArrayList<>();
                additionalInfos.add(Utils.getAdditionalInfo("Withdrawal Amount", String.valueOf(amount)));
                additionalInfos.add(Utils.getAdditionalInfo("Account Balance", String.valueOf(newBalance)));

                response = new Response("0", "Success", additionalInfos);

            } else {
                response = new Response("1", "Account Does not exist");
            }

        } catch (Exception e) {
            log.error("Error", e);
            response = new Response("1", "Could not withdrawal deposit transaction");
        }

        return response;
    }

    @Override
    public Response getTransactions(String accountNumber) {

        Response response = null;

        try {

            List<Transaction> transactionList;

            if (accountNumber == null) { //fetch all transaction

                transactionList = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

            } else {

                //get the all transactions for the account
                transactionList = transactionRepository.getAllAccountTransactionsInDescendingOrder(accountNumber);
            }

            response = new Response("0", "Success");
            response.setTransactions(transactionList);

        } catch (Exception e) {
            response = new Response("1", "Could not complete deposit transaction");
        }

        return response;
    }

}
