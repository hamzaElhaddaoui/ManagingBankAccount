package com.bank.managingbankaccount.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ParametersViolationException extends ResponseStatusException {

    public ParametersViolationException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }

}
