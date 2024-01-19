package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends R2dbcRepository<Transaction, UUID> {

    Flux<Transaction> findAllByMerchantIdAndCreatedAtBetween(Long merchantId, LocalDateTime startDate, LocalDateTime endDate);

    Flux<Transaction> findAllByStatus(String status);
}
