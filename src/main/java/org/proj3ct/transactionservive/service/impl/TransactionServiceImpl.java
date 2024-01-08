package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.repository.TransactionRepository;
import org.proj3ct.transactionservive.service.TransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Mono<Transaction> save(Transaction transaction) {
        LocalDateTime now = LocalDateTime.now();
        return transactionRepository.save(
                transaction.toBuilder()
                        .isNew(true)
                        .id(UUID.randomUUID())
                        .status(TransactionStatus.IN_PROCESS)
                        .createdAt(now)
                        .updatedAt(now)
                        .build());
    }

    @Override
    public Flux<Transaction> getAll(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getAllBetween(startDate, endDate);
    }

    @Override
    public Mono<Transaction> getById(UUID id) {
        return transactionRepository.findById(id);
    }
}
