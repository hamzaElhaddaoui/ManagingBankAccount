package com.bank.managingbankaccount.mapper;

import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.dto.AccountDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account toEntity(AccountDTO accountDTO);
    AccountDTO toDTO(Account account);

}
