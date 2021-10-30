package com.bank.managingbankaccount.mapper;

import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.dto.OperationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OperationMapper {

    Operation toEntity(OperationDTO operationDTO);
    OperationDTO toDTO(Operation operation);

}
