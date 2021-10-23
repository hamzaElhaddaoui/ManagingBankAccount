package com.bank.managingbankaccount.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;

}
