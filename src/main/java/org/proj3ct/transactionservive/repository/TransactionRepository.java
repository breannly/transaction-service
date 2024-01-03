package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends R2dbcRepository<Transaction, Long> {

    @Query("SELECT * FROM transactions WHERE created_at BETWEEN :startDate AND :endDate")
    Flux<Transaction> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);
}
