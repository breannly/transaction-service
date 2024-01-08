package org.proj3ct.transactionservive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.entity.transaction.Transaction;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    Transaction map(TransactionNewDto transactionNewDto);

    @Mapping(target = "externalTransactionId", source = "id")
    TransactionShortDto mapShort(Transaction transaction);

    @Mapping(target = "externalTransactionId", source = "id")
    TransactionDto map(Transaction transaction);

}
