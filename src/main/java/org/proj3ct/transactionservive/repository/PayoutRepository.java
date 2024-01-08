package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PayoutRepository extends R2dbcRepository<Payout, UUID> {

    @Query("SELECT * FROM payouts WHERE created_at BETWEEN :startDate AND :endDate")
    Flux<Payout> getAllBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT * FROM payouts WHERE status = :status")
    Flux<Payout> getAllByStatus(String status);
}
