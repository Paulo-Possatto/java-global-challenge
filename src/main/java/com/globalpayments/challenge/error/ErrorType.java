package com.globalpayments.challenge.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorType {
    private String httpCode;
    private String httpMessage;
    private String moreInformation;

    public String getHttpCode() {
        return httpCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public ErrorType(HttpStatus status, String moreInformation){
        getMessage(status);
        this.moreInformation = moreInformation;
    }

    private void getMessage(HttpStatus status){
        httpCode = String.valueOf(status.value());
        httpMessage = new StringBuilder(httpCode).append(" - ").append(status.getReasonPhrase()).toString();
    }
}
