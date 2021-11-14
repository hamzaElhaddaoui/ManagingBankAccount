package com.bank.managingbankaccount;

import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.dto.AccountDTO;
import com.bank.managingbankaccount.dto.ClientDTO;
import com.bank.managingbankaccount.mapper.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AccountMapperImpl.class, ClientMapperImpl.class, OperationMapperImpl.class})
public class AccountMapperTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testDtoToDomain(){
        AccountDTO accountDTO = AccountDTO.builder().amount(1000)
                                                    .balance(1000)
                                                    .name("Hamza account")
                                                    .clientDTO(ClientDTO.builder().firstName("hamza").lastName("EL HADDAOUI").build())
                                                    .build();

        Account account = accountMapper.toEntity(accountDTO);

        Assertions.assertEquals(account.getName(), accountDTO.getName());
        Assertions.assertEquals(account.getBalance(), accountDTO.getBalance());

        // test operation
        Assertions.assertNotNull(account.getOperations());
        Assertions.assertEquals(account.getOperations().size(), 1);
        Assertions.assertEquals(account.getOperations().get(0).getAmount(), accountDTO.getAmount());
        Assertions.assertEquals(account.getOperations().get(0).getOperationType(), OperationType.DIPOSITE);
        Assertions.assertNotNull(account.getOperations().get(0).getDate());

        // test client
        Assertions.assertEquals(account.getClient().getLastName(), accountDTO.getClientDTO().getLastName());
        Assertions.assertEquals(account.getClient().getFirstName(), accountDTO.getClientDTO().getFirstName());

    }

    @Test
    public void testFromEntityToDTO(){

        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().amount(1000).date(new Date()).operationType(OperationType.DIPOSITE).build());
        Account account = Account.builder()
                                .balance(1000)
                                .name("Hamza account")
                                .client(Client.builder().firstName("Hamza").lastName("EL HADDAOUI").build())
                                .operations(operations)
                                .build();
        AccountDTO accountDTO = accountMapper.toDTO(account);

        // test account normal fields
        Assertions.assertEquals(accountDTO.getName(), account.getName());
        Assertions.assertEquals(accountDTO.getBalance(), account.getBalance());

        // test compositions
        // Operations
        Assertions.assertEquals(accountDTO.getAmount(), account.getOperations().get(0).getAmount());

        // client
        Assertions.assertNotNull(accountDTO.getClientDTO());
        Assertions.assertEquals(accountDTO.getClientDTO().getFirstName(), account.getClient().getFirstName());
        Assertions.assertEquals(accountDTO.getClientDTO().getLastName(), account.getClient().getLastName());

    }
}
