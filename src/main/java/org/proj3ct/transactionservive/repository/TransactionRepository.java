package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TransactionRepository extends R2dbcRepository<Transaction, UUID> {

    @Query("SELECT * FROM transactions WHERE created_at BETWEEN :startDate AND :endDate")
    Flux<Transaction> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT * FROM transactions WHERE status = :status")
    Flux<Transaction> getAllByStatus(String status);
}
