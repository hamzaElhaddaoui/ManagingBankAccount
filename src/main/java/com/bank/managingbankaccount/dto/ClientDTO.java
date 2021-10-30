package com.bank.managingbankaccount.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;

}
