package com.minibank.mini.misc;


import com.minibank.mini.dto.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static AdditionalInfo getAdditionalInfo(String paramName, String paramValue) {

        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setParamName(paramName);
        additionalInfo.setParamValue(paramValue);

        return additionalInfo;
    }

    public static String getFourDigits(Long id) {
        String idAsString = "" + id;

        if (idAsString.length() == 1) {
            return "000" + idAsString;
        } else if (idAsString.length() == 2) {
            return "00" + idAsString;
        } else if (idAsString.length() == 3) {
            return "0" + idAsString;
        } else if (idAsString.length() == 4) {
            return idAsString;
        } else {
            return idAsString.substring(idAsString.length() - 4);
        }

    }

    public static String generateAccountNumber(Long id) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyssdd");
        String date = simpleDateFormat.format(new Date());

        //with the ID generate account Number
        //eleven digit
        //yyyymmdd + (id in 2 decimal places)

        return date + getFourDigits(id);
    }

    public static Response validateAccountDetails(AccountDto accountDto) {

        if (accountDto == null){
            return new Response("1", "Invalid request");
        }

        String name = accountDto.getName().trim();
        String phoneNumber = accountDto.getPhoneNumber().trim();

        if (name.isEmpty()){
            return new Response("1", "The Account Name is empty, Please provide a valid Name");
        }

        if (phoneNumber.isEmpty()){
            return new Response("1", "The Phone Number is empty, Please provide a valid Number");
        }

        String[] nameArray = name.split(" ");

        if (nameArray.length == 1){
            return new Response("1", "The Account Name just contains a single name, Please provide your full Name");
        }

        if (!isNumberValid(phoneNumber)){
            return new Response("1", "The Phone Number contains invalid characters, Please provide a valid Number");
        }

        return new Response("0", "Success");
    }

    public static boolean isValid(String param, String value) {
        return param.equalsIgnoreCase(value);
    }

    private static boolean isNumberValid(String number){
        return number.matches("^[0-9]+");
    }

    public static Response validateTransactionRequest(TransactionRequest request) {

        if (request == null){
            return new Response("1", "Invalid request");
        }

        String accountNumber = request.getAccountNumber().trim();
        String amount = request.getAmount().trim();

        if (accountNumber.isEmpty()){
            return new Response("1", "The Account Name is empty, Please provide a valid Name");
        }

        if (amount.isEmpty()){
            return new Response("1", "The Phone Number is empty, Please provide a valid Number");
        }

        if (accountNumber.length() != 10){
            return new Response("1", "The Account Number length is invalid, Please provide a valid Number");
        }

        if (!isNumberValid(accountNumber)){
            return new Response("1", "The Account Number contains invalid characters, Please provide a valid Number");
        }

        if (!isNumberValid(amount)){
            return new Response("1", "The Phone Number contains invalid characters, Please provide a valid Number");
        }

        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e){
            return new Response("1", "The Amount provided is invalid and not supported, Please provide a valid amount");
        }

        return new Response("0", "Success");

    }

    public static Response validateFetchTransactionRequest(FetchTransaction req) {

        if (req == null){
            return new Response("1", "Invalid request");
        }

        String accountNumber = req.getAccountNumber().trim();

        if (accountNumber.isEmpty()){//this is for case where alll the records need to be fetched
            return new Response("0", "Success");
        }

        if (accountNumber.length() != 10){
            return new Response("1", "The Account Number length is invalid, Please provide a valid Number");
        }

        if (!isNumberValid(accountNumber)){
            return new Response("1", "The Account Number contains invalid characters, Please provide a valid Number");
        }

        return new Response("0", "Success");
    }
}
