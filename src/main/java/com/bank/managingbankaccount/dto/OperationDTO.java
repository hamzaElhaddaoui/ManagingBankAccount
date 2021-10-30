package com.bank.managingbankaccount.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {
    private Long id;
    private int amount;
    private String operationType;
}