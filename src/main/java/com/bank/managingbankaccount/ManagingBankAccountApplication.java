package com.bank.managingbankaccount;

import com.bank.managingbankaccount.domain.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagingBankAccountApplication {

    public static void main(String[] args) {
        Client client = Client.builder().id(5L).firstName("Hamza").lastName("EL HADDAOUI").build();
        SpringApplication.run(ManagingBankAccountApplication.class, args);
    }

}
