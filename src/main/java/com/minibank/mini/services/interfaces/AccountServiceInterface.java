package com.minibank.mini.services.interfaces;

import com.minibank.mini.dto.AccountDto;
import com.minibank.mini.dto.Response;

public interface AccountServiceInterface {

    //account creation
    public Response createAccount(AccountDto accountDto);

    //account deposit
    public Response deposit(String toAccount, Double amount);

    //account withdrawal
    public Response withdrawal(String fromAccount, Double amount);

    //account transfer
    public Response getTransactions(String accountNumber);

}
