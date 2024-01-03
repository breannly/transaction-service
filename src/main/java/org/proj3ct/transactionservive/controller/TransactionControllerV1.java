package org.proj3ct.transactionservive.controller;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionListDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.mapper.TransactionMapper;
import org.proj3ct.transactionservive.service.TransactionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/transaction")
public class TransactionControllerV1 {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionShortDto> save(@RequestBody @Validated TransactionNewDto transactionNewDto) {
        Transaction transaction = transactionMapper.map(transactionNewDto);
        return transactionService.save(transaction)
                .map(transactionMapper::mapShort);
    }

    @GetMapping("/list")
    public Mono<TransactionListDto> getAll(@RequestParam(required = false) String start,
                                           @RequestParam(required = false) String end) {
        LocalDateTime startDate = (start != null ? LocalDate.parse(start) : LocalDate.now()).atStartOfDay();
        LocalDateTime endDate = (start != null ? LocalDate.parse(end) : LocalDate.now()).atTime(LocalTime.MAX);

        return transactionService.getAll(startDate, endDate)
                .map(transactionMapper::map).collectList()
                .map(TransactionListDto::of);
    }

    @GetMapping("/{transactionId}/details")
    public Mono<TransactionDto> getById(@PathVariable Long transactionId) {
        return transactionService.getById(transactionId)
                .map(transactionMapper::map);
    }
}
