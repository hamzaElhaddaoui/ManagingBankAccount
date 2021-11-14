package com.bank.managingbankaccount.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {
    private int amount;
    private String operationType;
    private Date date;
}