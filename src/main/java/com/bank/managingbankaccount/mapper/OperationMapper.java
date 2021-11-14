package com.bank.managingbankaccount.mapper;

import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.dto.OperationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationDTO toDTO(Operation operation);
    List<OperationDTO> toDTOs(List<Operation> operations);

}
