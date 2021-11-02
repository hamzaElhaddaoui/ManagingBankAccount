package com.bank.managingbankaccount.service;

import com.bank.managingbankaccount.Exceptions.ParametersViolationException;
import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.repository.AccountRepository;
import com.bank.managingbankaccount.repository.ClientRepository;
import com.bank.managingbankaccount.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

     private final AccountRepository accountRepository;

     private final ClientRepository clientRepository;

     private final OperationRepository operationRepository;

    /**
     * Create a bank account
     * @param account the account to create
     * @return the account created
     */
     public Account createAccount(Account account){
        Account acc;
        AccountService.log.debug("Entering the account service");
        // si account est null
        if(account == null){
            // run exception
            throw new ParametersViolationException("Entry parameter is null");
        }
        // if the balance equal the amount
        if(account.getBalance() != account.getOperations().get(0).getAmount()){
            // run exception
            throw new ParametersViolationException("The amount must be equal to the balance");
        }
        // verify id the account is already exist
        verifyAccount(account);

        // if client exist
        Optional<Client> opClient = clientRepository.findByFirstNameAndLastName(account.getClient().getFirstName(), account.getClient().getLastName());
        if(opClient.isPresent()){
            account.setClient(opClient.get());
            AccountService.log.debug("Client founded");
            acc = this.accountRepository.save(account);
            AccountService.log.info("Account is Created");
        }else{
            this.clientRepository.save(account.getClient());
            AccountService.log.info("Client is added");
            acc = this.accountRepository.save(account);
            AccountService.log.info("Account is created");
        }
        AccountService.log.debug("Exiting from the account service");
        return acc;
    }

    /**
     * Verify if the account is already created
     * @param account the account object
     */
    private void verifyAccount(Account account) {
        Optional<Account> opAccount = this.accountRepository.findByName(account.getName());
        // if the account exist
        if(opAccount.isPresent()){
            AccountService.log.info("The account is already created");
            // an exception will be thrown
            throw new ParametersViolationException("The account is already created");
        }
    }

    public Account withdrawMoney(String accountName, int amount) {
        // the amount must be greater than 0
        verifyAmount(amount);
        // account must exist
        Account account = getAccountIfExist(accountName);
        int balance = account.getBalance();
        if(balance - amount < -500){
            // throw exception
            throw new ParametersViolationException("Cannot process this operation please verify the balance");
        }
        // generate operation
        Operation operation = Operation.builder().amount(amount).operationType(OperationType.WITHDRAWAL).account(account).build();
        // substruct the balance
        account.setBalance(account.getBalance() - amount);
        account.getOperations().add(operation);
        // save account
        accountRepository.save(account);
        // return the results
        return account;
    }




    public Account depositMoney(String accountName, int amount) {
        // verify amount
        verifyAmount(amount);
        // verify account
        Account account = getAccountIfExist(accountName);
        // generate operation
        Operation operation = Operation.builder().amount(amount).operationType(OperationType.DIPOSITE).account(account).build();
        account.setBalance(account.getBalance() + amount);
        account.getOperations().add(operation);
        accountRepository.save(account);

        return account;
    }

    private void verifyAmount(int amount) {
        if(amount <= 0){
            // throw exception
            throw new ParametersViolationException("The amount must be greater then 0");
        }
    }

    private Account getAccountIfExist(String accountName) {
        Optional<Account> opAccount = this.accountRepository.findByName(accountName);
        if(!opAccount.isPresent()){
            // throw exception
            throw new ParametersViolationException("The account is not found");
        }
        // possibility to withdraw the amount
        Account account = opAccount.get();
        return account;
    }
}
