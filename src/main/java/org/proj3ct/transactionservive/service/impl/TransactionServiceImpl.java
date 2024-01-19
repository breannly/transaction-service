package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionListDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.mapper.TransactionMapper;
import org.proj3ct.transactionservive.repository.TransactionRepository;
import org.proj3ct.transactionservive.service.TransactionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<TransactionShortDto> save(Long merchantId, TransactionNewDto transactionNewDto) {
        Transaction transaction = transactionMapper.map(transactionNewDto);
        return transactionRepository.save(
                transaction.toBuilder()
                        .isNew(true)
                        .id(UUID.randomUUID())
                        .merchantId(merchantId)
                        .status(TransactionStatus.IN_PROCESS)
                        .build())
                .map(transactionMapper::mapShort);
    }

    @Override
    public Mono<TransactionListDto> getAll(Long merchantId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAllByMerchantIdAndCreatedAtBetween(merchantId, startDate, endDate)
                .map(transactionMapper::map).collectList()
                .map(TransactionListDto::of);
    }

    @Override
    public Mono<TransactionDto> getById(Long merchantId, UUID id) {
        return transactionRepository.findById(id)
                .filter(transaction -> transaction.getMerchantId().equals(merchantId))
                .map(transactionMapper::map);
    }
}
