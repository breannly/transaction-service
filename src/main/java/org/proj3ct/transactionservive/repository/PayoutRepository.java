package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PayoutRepository extends R2dbcRepository<Payout, UUID> {

    Flux<Payout> findAllByMerchantIdAndCreatedAtBetween(Long merchantId, LocalDateTime startDate, LocalDateTime endDate);

    Flux<Payout> findAllByStatus(String status);
}
