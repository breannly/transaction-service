package org.proj3ct.transactionservive.service;

import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionListDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionService {

    Mono<TransactionShortDto> save(Long merchantId, TransactionNewDto transactionNewDto);

    Mono<TransactionListDto> getAll(Long merchantId, LocalDateTime startDate, LocalDateTime endDate);

    Mono<TransactionDto> getById(Long merchantId, UUID id);

}
