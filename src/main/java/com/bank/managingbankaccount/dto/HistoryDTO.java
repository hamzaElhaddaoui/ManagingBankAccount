package com.bank.managingbankaccount.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HistoryDTO {
    int balance;
    List<OperationDTO> operations;
}
