package com.bank.managingbankaccount.controller;

import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.dto.AccountDTO;
import com.bank.managingbankaccount.mapper.AccountMapper;
import com.bank.managingbankaccount.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path="/api/bank")
@RequiredArgsConstructor
public class AccountController {

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    /**
     * Rest Post entry point used to create a bank account
     * @param accountDTO store data of the account to create
     * @return data of the account created
     */
    @PostMapping("/create/account")
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Server error"),
                        @ApiResponse(code = 404, message = "Service not found"),
                        @ApiResponse(code = 200, message = "Successful retrieval", response = AccountDTO.class)})
    @ApiOperation(value = "Save account in data base")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO accountDTO){
        AccountController.log.debug("Entering the add account method");
        // processing data
        Account account = accountMapper.toEntity(accountDTO);
        account = accountService.createAccount(account);
        AccountDTO rAccountDTO = accountMapper.toDTO(account);
        AccountController.log.debug(rAccountDTO.toString());
        // return result
        AccountController.log.debug("Exiting from the add account method");
        return ResponseEntity.ok(rAccountDTO);
    }


    @PutMapping("/{accountName}/withdraw")
    public ResponseEntity<AccountDTO> withdrawMoney(@PathVariable String accountName, @RequestParam int amount){
        Account account = accountService.withdrawMoney(accountName, amount);
        AccountDTO accountDTO = accountMapper.toDTO(account);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{accountName}/deposit")
    public ResponseEntity<AccountDTO> depositMoney(@PathVariable String accountName, @RequestParam int amount){
        Account account = accountService.depositMoney(accountName, amount);
        AccountDTO accountDTO = accountMapper.toDTO(account);
        return ResponseEntity.ok(accountDTO);
    }


}
