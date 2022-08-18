package com.minibank.mini.controller;

import com.minibank.mini.dto.AccountDto;
import com.minibank.mini.dto.FetchTransaction;
import com.minibank.mini.dto.Response;
import com.minibank.mini.dto.TransactionRequest;
import com.minibank.mini.misc.Utils;
import com.minibank.mini.models.Transaction;
import com.minibank.mini.services.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
public class BaseController {

    @Autowired
    AccountServiceInterface accountService;

    @CrossOrigin
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Response CreateAccount(@RequestBody AccountDto accountDto){

        Response response = Utils.validateAccountDetails(accountDto);

        if (!Utils.isValid("0", response.getResponseCode())){
            return response;
        }

        return accountService.createAccount(accountDto);
    }

    @CrossOrigin
    @PostMapping(value = "/deposit", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Response depositAccount(@RequestBody TransactionRequest request){

        Response response = Utils.validateTransactionRequest(request);

        if (!Utils.isValid("0", response.getResponseCode())){
            return response;
        }

        return accountService.deposit(request.getAccountNumber(), Double.valueOf(request.getAmount()));
    }

    @CrossOrigin
    @PostMapping(value = "/withdraw", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Response withdrawFromAccount(@RequestBody TransactionRequest request){

        Response response = Utils.validateTransactionRequest(request);

        if (!Utils.isValid("0", response.getResponseCode())){
            return response;
        }

        return accountService.withdrawal(request.getAccountNumber(), Double.valueOf(request.getAmount()));
    }

    @CrossOrigin
    @PostMapping(value = "/transactions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Response getTransaction(@RequestBody FetchTransaction req){

        Response response = Utils.validateFetchTransactionRequest(req);

        if (!Utils.isValid("0", response.getResponseCode())){
            return response;
        }

        return accountService.getTransactions(req.getAccountNumber().trim().isEmpty() ? null :  req.getAccountNumber().trim());
    }

}
