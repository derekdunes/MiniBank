package com.minibank.mini.services;

import com.minibank.mini.dto.AccountDto;
import com.minibank.mini.dto.Response;
import com.minibank.mini.enums.TransactionType;
import com.minibank.mini.services.interfaces.AccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class AccountServiceTest {

    @Autowired
    AccountServiceInterface accountService;

    @Test
    @Transactional
    public void CreateAccountTests() {

        AccountDto accountDto = new AccountDto();
        accountDto.setName("Mbah Derek");
        accountDto.setPhoneNumber("08169122379");

        Response response = accountService.createAccount(accountDto);

        log.debug(response.toString());
        assertEquals("0", response.getResponseCode());
        assertEquals("Success", response.getResponseMessage());

    }

    @Nested
    class DepositTests {

        @Test
        @Transactional
        public void DepositIntoAccountTests() {

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), 100.0);

            assertEquals("0", response1.getResponseCode());
            assertEquals("Success", response1.getResponseMessage());

            assertEquals("Deposited Amount", response1.getAdditionalInfo().get(0).getParamName());
            assertEquals("100.0", response1.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response1.getAdditionalInfo().get(1).getParamName());
            assertEquals("100.0", response1.getAdditionalInfo().get(1).getParamValue());
        }

        @Test
        @Transactional
        public void DepositInvalidNumbersIntoAccountTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), -100.0);

            assertEquals("1", response1.getResponseCode());
            assertEquals("Amount provided is invalid, please provide a valid amount", response1.getResponseMessage());

        }

        @Test
        @Transactional
        public void DepositIntoInvalidAccountTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit("11234", 100.0);

            assertEquals("1", response1.getResponseCode());
            assertEquals("Account Does not exist", response1.getResponseMessage());

        }

    }

    @Nested
    class withdrawTests {

        @Test
        @Transactional
        public void withdrawFromInvalidAccountTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), 1000.0);

            assertEquals("0", response1.getResponseCode());
            assertEquals("Success", response1.getResponseMessage());

            assertEquals("Deposited Amount", response1.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response1.getAdditionalInfo().get(1).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(1).getParamValue());

            Response response2 = accountService.withdrawal("1218121", 20.0);

            assertEquals("1", response2.getResponseCode());
            assertEquals("Account Does not exist", response2.getResponseMessage());

        }

        @Test
        @Transactional
        public void withdrawFromAmountGreaterThanBalanceTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), 1000.0);

            assertEquals("0", response1.getResponseCode());
            assertEquals("Success", response1.getResponseMessage());

            assertEquals("Deposited Amount", response1.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response1.getAdditionalInfo().get(1).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(1).getParamValue());

            Response response2 = accountService.withdrawal(response.getAdditionalInfo().get(0).getParamValue(), 10000.0);

            assertEquals("1", response2.getResponseCode());
            assertEquals("Your account balance is too small for the withdrawal amount", response2.getResponseMessage());

        }

        @Test
        @Transactional
        public void withdrawEveryThingTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), 1000.0);

            assertEquals("0", response1.getResponseCode());
            assertEquals("Success", response1.getResponseMessage());

            assertEquals("Deposited Amount", response1.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response1.getAdditionalInfo().get(1).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(1).getParamValue());

            Response response2 = accountService.withdrawal(response.getAdditionalInfo().get(0).getParamValue(), 1000.0);

            assertEquals("0", response2.getResponseCode());
            assertEquals("Success", response2.getResponseMessage());

            assertEquals("Withdrawal Amount", response2.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", response2.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response2.getAdditionalInfo().get(1).getParamName());
            assertEquals("0.0", response2.getAdditionalInfo().get(1).getParamValue());

        }

        @Test
        @Transactional
        public void withdrawFromAccountTests(){

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            log.debug(response.toString());

            assertEquals("0", response.getResponseCode());
            assertEquals("Success", response.getResponseMessage());
            assertEquals("account Number", response.getAdditionalInfo().get(0).getParamName());

            Response response1 = accountService.deposit(response.getAdditionalInfo().get(0).getParamValue(), 1000.0);

            assertEquals("0", response1.getResponseCode());
            assertEquals("Success", response1.getResponseMessage());

            assertEquals("Deposited Amount", response1.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response1.getAdditionalInfo().get(1).getParamName());
            assertEquals("1000.0", response1.getAdditionalInfo().get(1).getParamValue());

            Response response2 = accountService.withdrawal(response.getAdditionalInfo().get(0).getParamValue(), 20.0);

            assertEquals("0", response2.getResponseCode());
            assertEquals("Success", response2.getResponseMessage());

            assertEquals("Withdrawal Amount", response2.getAdditionalInfo().get(0).getParamName());
            assertEquals("20.0", response2.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", response2.getAdditionalInfo().get(1).getParamName());
            assertEquals("980.0", response2.getAdditionalInfo().get(1).getParamValue());

        }

    }

    @Nested
    class TransactionTests {

        @Test
        @Transactional
        public void gettransactionsWithAccountTests() {

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response createAccountResponse = accountService.createAccount(accountDto);

            log.debug(createAccountResponse.toString());

            assertEquals("0", createAccountResponse.getResponseCode());
            assertEquals("Success", createAccountResponse.getResponseMessage());
            assertEquals("account Number", createAccountResponse.getAdditionalInfo().get(0).getParamName());

            String accountNumber = createAccountResponse.getAdditionalInfo().get(0).getParamValue();

            Response depositResponse = accountService.deposit(accountNumber, 1000.0);

            assertEquals("0", depositResponse.getResponseCode());
            assertEquals("Success", depositResponse.getResponseMessage());

            assertEquals("Deposited Amount", depositResponse.getAdditionalInfo().get(0).getParamName());
            assertEquals("1000.0", depositResponse.getAdditionalInfo().get(0).getParamValue());
            assertEquals("Account Balance", depositResponse.getAdditionalInfo().get(1).getParamName());
            assertEquals("1000.0", depositResponse.getAdditionalInfo().get(1).getParamValue());

            Response withdrawalResponse = accountService.withdrawal(accountNumber, 20.0);

            assertEquals("0", withdrawalResponse.getResponseCode());
            assertEquals("Success", withdrawalResponse.getResponseMessage());

            Response transactionsResponse = accountService.getTransactions(accountNumber);

            assertEquals("0", transactionsResponse.getResponseCode());
            assertEquals("Success", transactionsResponse.getResponseMessage());
            assertEquals(2, transactionsResponse.getTransactions().size());

            //latest trsanctions to oldest
            assertEquals(accountNumber, transactionsResponse.getTransactions().get(0).getFromAccount());
            assertEquals(accountNumber, transactionsResponse.getTransactions().get(0).getToAccount());
            assertEquals(20.0, transactionsResponse.getTransactions().get(0).getAmount());
            assertEquals("WITHDRAWAL",transactionsResponse.getTransactions().get(0).getDescription());
            assertEquals(TransactionType.WITHDRAWAL,transactionsResponse.getTransactions().get(0).getType());

            assertEquals(accountNumber, transactionsResponse.getTransactions().get(1).getFromAccount());
            assertEquals(accountNumber, transactionsResponse.getTransactions().get(1).getToAccount());
            assertEquals(1000.0, transactionsResponse.getTransactions().get(1).getAmount());
            assertEquals("DEPOSIT",transactionsResponse.getTransactions().get(1).getDescription());
            assertEquals(TransactionType.DEPOSIT,transactionsResponse.getTransactions().get(1).getType());

        }

        @Test
        @Transactional
        public void transactionswithoutAccountsTests() {

            AccountDto accountDto = new AccountDto();
            accountDto.setName("Mbah Derek");
            accountDto.setPhoneNumber("08169122379");

            Response response = accountService.createAccount(accountDto);

            AccountDto accountDto2 = new AccountDto();
            accountDto2.setName("Mbah Stephen");
            accountDto2.setPhoneNumber("0803672484");

            Response AcountResponse = accountService.createAccount(accountDto2);

            String accountNumber1 = response.getAdditionalInfo().get(0).getParamValue();
            String accountNumber2 = AcountResponse.getAdditionalInfo().get(0).getParamValue();

            accountService.deposit(accountNumber1, 1000.0);
            accountService.deposit(accountNumber2, 2000.0);

            accountService.withdrawal(accountNumber1, 20.0);
            accountService.withdrawal(accountNumber2, 200.0);

            Response transactionsResponse = accountService.getTransactions(null);

            assertEquals("0", transactionsResponse.getResponseCode());
            assertEquals("Success", transactionsResponse.getResponseMessage());
            assertEquals(4, transactionsResponse.getTransactions().size());

            //latest trsanctions to oldest
            assertEquals(accountNumber2, transactionsResponse.getTransactions().get(0).getFromAccount());
            assertEquals(accountNumber2, transactionsResponse.getTransactions().get(0).getToAccount());
            assertEquals(200.0, transactionsResponse.getTransactions().get(0).getAmount());
            assertEquals("WITHDRAWAL",transactionsResponse.getTransactions().get(0).getDescription());
            assertEquals(TransactionType.WITHDRAWAL,transactionsResponse.getTransactions().get(0).getType());

            assertEquals(accountNumber1, transactionsResponse.getTransactions().get(1).getFromAccount());
            assertEquals(accountNumber1, transactionsResponse.getTransactions().get(1).getToAccount());
            assertEquals(20.0, transactionsResponse.getTransactions().get(1).getAmount());
            assertEquals("WITHDRAWAL",transactionsResponse.getTransactions().get(1).getDescription());
            assertEquals(TransactionType.WITHDRAWAL,transactionsResponse.getTransactions().get(1).getType());

            assertEquals(accountNumber2, transactionsResponse.getTransactions().get(2).getFromAccount());
            assertEquals(accountNumber2, transactionsResponse.getTransactions().get(2).getToAccount());
            assertEquals(2000.0, transactionsResponse.getTransactions().get(2).getAmount());
            assertEquals("DEPOSIT",transactionsResponse.getTransactions().get(2).getDescription());
            assertEquals(TransactionType.DEPOSIT,transactionsResponse.getTransactions().get(2).getType());

            assertEquals(accountNumber1, transactionsResponse.getTransactions().get(3).getFromAccount());
            assertEquals(accountNumber1, transactionsResponse.getTransactions().get(3).getToAccount());
            assertEquals(1000.0, transactionsResponse.getTransactions().get(3).getAmount());
            assertEquals("DEPOSIT",transactionsResponse.getTransactions().get(3).getDescription());
            assertEquals(TransactionType.DEPOSIT,transactionsResponse.getTransactions().get(3).getType());

        }

    }

}
