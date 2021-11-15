package mapper;

import com.bank.managingbankaccount.domain.Operation;
import com.bank.managingbankaccount.domain.OperationType;
import com.bank.managingbankaccount.dto.OperationDTO;
import com.bank.managingbankaccount.mapper.OperationMapper;
import com.bank.managingbankaccount.mapper.OperationMapperImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OperationMapperImpl.class)
public class OperationMapperTest {

    @Autowired
    OperationMapper operationMapper;

    @Test
    public void testEntityToDTO(){
        Operation operation = Operation.builder().amount(1000).operationType(OperationType.WITHDRAWAL).date(new Date()).build();
        OperationDTO operationDTO = operationMapper.toDTO(operation);

        Assertions.assertEquals(operationDTO.getAmount(), operation.getAmount());
        Assertions.assertEquals(operationDTO.getDate().toString(), operation.getDate().toString());
        Assertions.assertEquals(operationDTO.getOperationType(), operation.getOperationType().toString());

    }

    @Test
    public void testEntitiesToDTOs(){
        List<Operation> operations = new ArrayList<>();

        operations.add(Operation.builder().operationType(OperationType.WITHDRAWAL).amount(1000).date(new Date()).build());
        operations.add(Operation.builder().operationType(OperationType.DIPOSITE).amount(1500).date(new Date()).build());
        operations.add(Operation.builder().operationType(OperationType.WITHDRAWAL).amount(500).date(new Date()).build());

        List<OperationDTO> operationDTOs = operationMapper.toDTOs(operations);

        Assertions.assertEquals(operationDTOs.size(), operations.size());
        Assertions.assertEquals(operationDTOs.get(0).getAmount(), operations.get(0).getAmount());
        Assertions.assertEquals(operationDTOs.get(0).getOperationType(), operations.get(0).getOperationType().toString());
        Assertions.assertEquals(operationDTOs.get(0).getDate().toString(), operations.get(0).getDate().toString());

        Assertions.assertEquals(operationDTOs.get(2).getAmount(), operations.get(2).getAmount());
        Assertions.assertEquals(operationDTOs.get(2).getOperationType(), operations.get(2).getOperationType().toString());
        Assertions.assertEquals(operationDTOs.get(2).getDate().toString(), operations.get(2).getDate().toString());
    }
}
