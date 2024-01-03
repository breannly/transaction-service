package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface PayoutRepository extends R2dbcRepository<Payout, Long> {

    @Query("SELECT * FROM payouts WHERE created_at BETWEEN :startDate AND :endDate")
    Flux<Transaction> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);
}
