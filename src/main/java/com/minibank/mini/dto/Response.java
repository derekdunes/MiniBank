package com.minibank.mini.dto;

import com.minibank.mini.models.Transaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response {

    private String responseCode;

    private String responseMessage;

    private List<AdditionalInfo> additionalInfo;

    private List<Transaction> transactions;

    public Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public Response(String responseCode, String responseMessage, List<AdditionalInfo> additionalInfo) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.additionalInfo = additionalInfo;
    }

}
