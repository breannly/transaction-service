package org.proj3ct.transactionservive.service;

import org.proj3ct.transactionservive.entity.payout.Payout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PayoutService {

    Mono<Payout> save(Payout payout);

    Flux<Payout> getAll(LocalDateTime startDate, LocalDateTime endDate);

    Mono<Payout> getById(Long id);

}
