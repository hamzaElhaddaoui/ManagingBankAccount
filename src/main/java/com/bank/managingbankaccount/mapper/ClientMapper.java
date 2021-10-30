package com.bank.managingbankaccount.mapper;

import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    Client getEntity(ClientDTO clientDTO);
    ClientDTO getDTO(Client client);
}
