package org.proj3ct.transactionservive.service;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionService {

    Mono<Transaction> save(Transaction transaction);

    Flux<Transaction> getAll(LocalDateTime startDate, LocalDateTime endDate);

    Mono<Transaction> getById(UUID id);

}
