package org.proj3ct.transactionservive.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionListDto {

    private List<TransactionDto> transactionList;

    public static TransactionListDto of(List<TransactionDto> list) {
        TransactionListDto transactionListDto = new TransactionListDto();
        transactionListDto.setTransactionList(list);
        return transactionListDto;
    }
}
