package controller;

import com.bank.managingbankaccount.ManagingBankAccountApplication;
import com.bank.managingbankaccount.controller.AccountController;
import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.dto.AccountDTO;
import com.bank.managingbankaccount.dto.ClientDTO;
import com.bank.managingbankaccount.mapper.AccountMapper;
import com.bank.managingbankaccount.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//https://reflectoring.io/spring-boot-web-controller-test/


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountController.class)
@ContextConfiguration(classes ={ManagingBankAccountApplication.class})
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init(){
        Client client = Client.builder().id(1L).firstName("Hamza").lastName("EL HADDAOUI").build();
        List<Operation> operations = new ArrayList<>();
        operations.add(Operation.builder().id(3L).amount(1000).operationType(OperationType.DIPOSITE).date(new Date()).build());
        Account account = Account.builder().id(2L).client(client).balance(1000).name("Hamza's account").operations(operations).build();
        AccountDTO accountDTO = AccountDTO.builder().balance(1000).amount(1000).name("Hamza's account").clientDTO(ClientDTO.builder().firstName("Hamza").lastName("EL HADDAOUI").build()).build();
        Mockito.when(accountService.createAccount(Mockito.any(Account.class))).thenReturn(account);
        Mockito.when(accountMapper.toDTO(Mockito.any(Account.class))).thenReturn(accountDTO);
        Mockito.when(accountMapper.toEntity(Mockito.any(AccountDTO.class))).thenReturn(account);
    }

    @Test
    public void AccountCreationRequest() throws Exception{

        AccountDTO accountDTO = AccountDTO.builder().balance(1000).amount(1000).name("Hamza's account").clientDTO(ClientDTO.builder().firstName("Hamza").lastName("EL HADDAOUI").build()).build();

        // testing the http request with the parameters, serialization phase and the response.
        MvcResult mvcResult = mockMvc.perform(post("/api/bank/create/account")
                        .content(mapper.writeValueAsString(accountDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(accountDTO.getName()))
                        .andExpect(jsonPath("$.balance").value(accountDTO.getBalance()))
                        .andExpect(jsonPath("$.amount").value(accountDTO.getAmount()))
                        .andReturn();

        // Testing the calling of the business layer with the correct parameters
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountService, times(1)).createAccount(accountArgumentCaptor.capture());
        Assertions.assertEquals(accountArgumentCaptor.getValue().getBalance(), accountDTO.getBalance());
        Assertions.assertEquals(accountArgumentCaptor.getValue().getOperations().get(0).getAmount(), accountDTO.getAmount());
        Assertions.assertEquals(accountArgumentCaptor.getValue().getName(), accountDTO.getName());

        // testing the response
        String responseBody = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(responseBody.trim(), mapper.writeValueAsString(accountDTO).trim());

    }

    @Test
    public void AccountCreationRequestWithNullParameter() throws Exception {
        // accountDTO with null client
        AccountDTO accountDTO = AccountDTO.builder().balance(1000).name("Hamza's account").build();
        mockMvc.perform(post("/api/bank/create/account")
                .content(mapper.writeValueAsString(accountDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
