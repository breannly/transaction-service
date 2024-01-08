package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.repository.PayoutRepository;
import org.proj3ct.transactionservive.service.PayoutService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutRepository payoutRepository;

    @Override
    public Mono<Payout> save(Payout payout) {
        LocalDateTime now = LocalDateTime.now();
        return payoutRepository.save(
                        payout.toBuilder()
                                .isNew(true)
                                .id(UUID.randomUUID())
                                .status(PayoutStatus.IN_PROGRESS)
                                .createdAt(now)
                                .updatedAt(now)
                                .build());
    }

    @Override
    public Flux<Payout> getAll(LocalDateTime startDate, LocalDateTime endDate) {
        return payoutRepository.getAllBetween(startDate, endDate);
    }

    @Override
    public Mono<Payout> getById(UUID id) {
        return payoutRepository.findById(id);
    }
}
