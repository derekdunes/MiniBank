package com.minibank.mini.controller;

import com.minibank.mini.dto.AccountDto;
import com.minibank.mini.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BaseControllerTests {

    @Test
    public void createAccountTest() {

        AccountDto accountDto = new AccountDto();
        accountDto.setName("mbah derek");
        accountDto.setPhoneNumber("08169122379");

        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto);

        ResponseEntity<Response> response = new RestTemplate().exchange("/account/create", HttpMethod.POST, entity, Response.class);

        if (HttpURLConnection.HTTP_OK != response.getStatusCodeValue()) {

            Response mainResponse  = response.getBody();

        } else {
            //log.debug("response code: {}, url: {} ", response.getStatusCodeValue(), params.getTarget());
        }

//        assertEquals(200,  response.getHttpStatusCode());
//        assertEquals("Retrieved subscriber bio data for the specified msisdn",  response.getBody().getMessage());
//        assertEquals( false, response.getBody().getBioData().isReRegUpdateEligible());
//        assertEquals( false, response.getBody().getBioData().isBiometricUpdateEligible());
//        assertEquals( "ANTHONY",  response.getBody().getBioData().getNimcDemoData().getFirstname());
//        assertEquals("CHINEDUBO", response.getBody().getBioData().getNimcDemoData().getMiddlename());
//        assertEquals( "UZOH", response.getBody().getBioData().getNimcDemoData().getSurname());
//        assertEquals( "03-10-1977", response.getBody().getBioData().getNimcDemoData().getBirthdate());
    }


}
