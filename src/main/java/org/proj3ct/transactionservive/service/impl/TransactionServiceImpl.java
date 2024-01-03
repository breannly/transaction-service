package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.manager.TransactionManager;
import org.proj3ct.transactionservive.manager.WebhookManager;
import org.proj3ct.transactionservive.repository.TransactionRepository;
import org.proj3ct.transactionservive.service.TransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionManager transactionManager;
    private final WebhookManager webhookManager;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        LocalDateTime now = LocalDateTime.now();
        return transactionRepository.save(
                transaction.toBuilder()
                        .status(TransactionStatus.IN_PROCESS)
                        .createdAt(now)
                        .updatedAt(now)
                        .build())
                .doOnSuccess(t -> processTransaction(t).subscribe());
    }

    private Mono<Void> processTransaction(Transaction transaction) {
        return Mono.just(transaction)
                .map(transactionManager::manage)
                .flatMap(transactionRepository::save)
                .flatMap(webhookManager::sendNotification)
                .then();
    }

    @Override
    public Flux<Transaction> getAll(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getAllBetween(startDate, endDate);
    }

    @Override
    public Mono<Transaction> getById(Long id) {
        return transactionRepository.findById(id);
    }
}
