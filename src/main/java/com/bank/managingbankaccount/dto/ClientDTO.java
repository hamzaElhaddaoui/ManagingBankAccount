package com.bank.managingbankaccount.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
