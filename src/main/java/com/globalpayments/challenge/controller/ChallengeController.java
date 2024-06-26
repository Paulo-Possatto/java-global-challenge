package com.globalpayments.challenge.controller;

import com.globalpayments.challenge.dto.RequestDto;
import com.globalpayments.challenge.error.ErrorType;
import com.globalpayments.challenge.helper.ConstantHelper;
import com.globalpayments.challenge.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(ConstantHelper.MAIN_PATH)
public class ChallengeController {

    @Autowired
    private Service service;

    @PostMapping(ConstantHelper.CAR_PATH)
    public ResponseEntity<Object> rentCar(
            @RequestBody List<RequestDto> request
            ){
        Object res = service.getCar(request);
        if(res instanceof ErrorType){
            ErrorType err = (ErrorType) res;
            int errorCode = Integer.valueOf(err.getHttpCode());
            return new ResponseEntity<>(err.getMoreInformation(), HttpStatusCode.valueOf(errorCode));
        } else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}
