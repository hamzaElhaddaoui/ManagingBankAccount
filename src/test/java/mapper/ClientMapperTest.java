package mapper;

import com.bank.managingbankaccount.domain.Client;
import com.bank.managingbankaccount.dto.ClientDTO;
import com.bank.managingbankaccount.mapper.ClientMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientMapperTest {

    private ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @Test
    public void testDtoToDomain(){

        ClientDTO clientDTO = ClientDTO.builder().firstName("Hamza").lastName("EL HADDAOUI").build();

        Client client = mapper.getEntity(clientDTO);

        Assertions.assertEquals(client.getFirstName(),clientDTO.getFirstName());
        Assertions.assertEquals(client.getLastName(),clientDTO.getLastName());

    }

    @Test
    public void testDomainToDto(){

        Client client = Client.builder().id(5L).firstName("Hamza").lastName("EL HADDAOUI").build();

        ClientDTO clientDTO = mapper.getDTO(client);

        Assertions.assertEquals(client.getFirstName(), clientDTO.getFirstName());
        Assertions.assertEquals(client.getLastName(), clientDTO.getLastName());
    }
}
