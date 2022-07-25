package com.minibank.mini.misc;

import com.minibank.mini.dto.AdditionalInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
public class UtilsTests {

    @ParameterizedTest
    @CsvSource({"Deposit, 100", "Withdraw, 500", "Create, 5000"})
    public void getAdditionalInfoTest(String paramName, String paramValue){

        AdditionalInfo additionalInfo = Utils.getAdditionalInfo(paramName, paramValue);

        assertEquals(paramName, additionalInfo.getParamName());
        assertEquals(paramValue, additionalInfo.getParamValue());

    }

    @ParameterizedTest
    @CsvSource({"100, 0100", "19999, 9999", "19, 0019", "15, 0015", "1, 0001", "199293923, 3923"})
    public void get4DecimalPlacesTest(String number, String LastDigit){

        String lastNum =  Utils.getFourDigits(Long.parseLong(number));

        assertEquals(LastDigit, lastNum);

    }

    @Test
    public void generateAccountNumber(){

        String[] accountNumberList = new String[1000];

        for (int x = 0; x < accountNumberList.length; x++){
            accountNumberList[x] =  Utils.generateAccountNumber((long) x);
        }

        for (int x = 0; x < accountNumberList.length; x++){

            for (int y = x + 1; y < accountNumberList.length; y++){
                assertNotEquals(accountNumberList[x], accountNumberList[y]);
            }

        }
    }

}
