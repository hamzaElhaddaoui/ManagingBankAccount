package com.bank.managingbankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AccountDTO {

    @NotNull(message = "amount cannot be null")
    private int amount;

    @NotNull(message = "balance cannot be null")
    private int balance;

    @Valid
    @NotNull(message = "Client cannot be null")
    @JsonProperty("client")
    private ClientDTO clientDTO;

    @NotNull(message = "Name cannot be null")
    private String name;

}
