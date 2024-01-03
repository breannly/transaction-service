package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.manager.PayoutManager;
import org.proj3ct.transactionservive.manager.WebhookManager;
import org.proj3ct.transactionservive.repository.PayoutRepository;
import org.proj3ct.transactionservive.service.PayoutService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutManager payoutManager;
    private final PayoutRepository payoutRepository;
    private final WebhookManager webhookManager;

    @Override
    public Mono<Payout> save(Payout payout) {
        LocalDateTime now = LocalDateTime.now();
        return payoutRepository.save(
                        payout.toBuilder()
                                .status(PayoutStatus.IN_PROGRESS)
                                .createdAt(now)
                                .updatedAt(now)
                                .build())
                .doOnSuccess(t -> processTransaction(t).subscribe());
    }

    private Mono<Void> processTransaction(Payout payout) {
        return Mono.just(payout)
                .map(payoutManager::manage)
                .flatMap(payoutRepository::save)
                .flatMap(webhookManager::sendNotification)
                .then();
    }

    @Override
    public Flux<Payout> getAll(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public Mono<Payout> getById(Long id) {
        return payoutRepository.findById(id);
    }
}
