package com.bank.managingbankaccount.service;

import com.bank.managingbankaccount.Exceptions.ParametersViolationException;
import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.dto.HistoryDTO;
import com.bank.managingbankaccount.mapper.OperationMapper;
import com.bank.managingbankaccount.repository.AccountRepository;
import com.bank.managingbankaccount.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

     private final AccountRepository accountRepository;

     private final ClientRepository clientRepository;

     private final OperationMapper operationMapper;

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
        // add account reference to the first operation (first deposit)
         account.getOperations().get(0).setAccount(account);
        // if client exist
        Optional<Client> opClient = clientRepository.findByFirstNameAndLastName(account.getClient().getFirstName(), account.getClient().getLastName());
        if(opClient.isPresent()){
            account.setClient(opClient.get());
            AccountService.log.info("Client founded");
            AccountService.log.debug(account.toString());
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

    /**
     * withdraw an amount from an account
     * @param accountName the name of account to withdraw
     * @param amount the amount
     * @return the account
     */
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
        Operation operation = Operation.builder().amount(amount).operationType(OperationType.WITHDRAWAL).account(account).date(new Date()).build();
        // substruct the balance
        account.setBalance(account.getBalance() - amount);
        account.getOperations().add(operation);
        AccountService.log.info("the withdraw operation is done");
        // save account
        accountRepository.save(account);
        // return the results
        return account;
    }


    /**
     * operation of deposing money in account
     * @param accountName the account name
     * @param amount amount to deposit
     * @return the account
     */
    public Account depositMoney(String accountName, int amount) {
        // verify amount
        verifyAmount(amount);
        // verify account
        Account account = getAccountIfExist(accountName);
        // generate operation
        Operation operation = Operation.builder().amount(amount).operationType(OperationType.DIPOSITE).account(account).date(new Date()).build();
        account.setBalance(account.getBalance() + amount);
        account.getOperations().add(operation);
        AccountService.log.info("the deposit operation is done");
        accountRepository.save(account);

        return account;
    }

    /**
     * Validate the amount
     * @param amount the amount
     */
    private void verifyAmount(int amount) {
        if(amount <= 0){
            // throw exception
            throw new ParametersViolationException("The amount must be greater then 0");
        }
    }

    /**
     * Get the account by name
     * @param accountName name of account
     * @return the searched account
     */
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

    /**
     * used to get the transactions history of an account
     * @param accountName
     * @param page
     * @param size
     * @return
     */
    public HistoryDTO getAccountHistory(String accountName, int page, int size) {
        Optional<Account> opAccount = this.accountRepository.findByName(accountName);
        if(!opAccount.isPresent()){
            throw new ParametersViolationException("Account name doesn't exist");
        }
        Account account = opAccount.get();
        AccountService.log.debug(account.toString());
        List<Operation> operations = account.getOperations();
        if(operations.size() < (page-1) * size){
            throw  new ParametersViolationException("Page requested in history is not available");
        }
        int indexMax = Math.min(operations.size(), page * size);
        int indexStart = (page-1) * size;
        List<Operation> operationsRequested = operations.subList(indexStart, indexMax);
        HistoryDTO historyDTO = HistoryDTO.builder().balance(account.getBalance()).operations(operationMapper.toDTOs(operationsRequested)).build();
        return historyDTO;
    }
}
