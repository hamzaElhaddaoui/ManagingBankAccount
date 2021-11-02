package com.bank.managingbankaccount.mapper;

import com.bank.managingbankaccount.domain.Account;
import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {ClientMapper.class})
public interface AccountMapper {

    @Mapping(target = "client", source = "clientDTO")
    @Mapping(target = "operations", source = "amount", qualifiedByName = "getOperations")
    Account toEntity(AccountDTO accountDTO);

    @Mapping(target = "clientDTO", source = "client")
    @Mapping(target = "amount", source="operations", qualifiedByName = "assignAmount")
    AccountDTO toDTO(Account account);

    @Named("assignAmount")
    default int assignAmount(List<Operation> operations){
        return operations.get(operations.size()-1).getAmount();
    }

    @Named("getOperations")
    default List<Operation> from(int amount){
        List<Operation> list = new ArrayList<>();
        list.add(Operation.builder().amount(amount).operationType(OperationType.DIPOSITE).build());
        return list;
    }

}
