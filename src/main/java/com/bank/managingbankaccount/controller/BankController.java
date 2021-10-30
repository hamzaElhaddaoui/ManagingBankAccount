package com.bank.managingbankaccount.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path="/api/bank")
public class BankController {

    @PostMapping("")
    public ResponseEntity createAccount(){
        BankController.log.debug("Entering the add account method");

        BankController.log.debug("Exiting from the add account method");
        return null;
    }
}
