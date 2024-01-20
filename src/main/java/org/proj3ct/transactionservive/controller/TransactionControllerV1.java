package org.proj3ct.transactionservive.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionListDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.service.TransactionService;
import org.proj3ct.transactionservive.utils.CredentialsUtils;
import org.proj3ct.transactionservive.validator.AuthorizationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/transaction")
public class TransactionControllerV1 {

    private final AuthorizationValidator authenticationValidator;
    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionShortDto> save(@RequestBody @Validated TransactionNewDto transactionNewDto,
                                          @RequestHeader("Authorization") String authHeader) {
        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {} - Transaction Save Attempt: {}", merchantId, transactionNewDto);
                    return transactionService.save(merchantId, transactionNewDto)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Saved Transaction: {}", merchantId, response));
                });
    }

    @GetMapping("/list")
    public Mono<TransactionListDto> getAll(@RequestParam(required = false) Long start,
                                           @RequestParam(required = false) Long end,
                                           @RequestHeader("Authorization") String authHeader) {
        LocalDateTime startDate =
                (start != null ? LocalDate.ofInstant(Instant.ofEpochSecond(start), ZoneId.systemDefault()) : LocalDate.now()).atStartOfDay();
        LocalDateTime endDate =
                (start != null ? LocalDate.ofInstant(Instant.ofEpochSecond(end), ZoneId.systemDefault()) : LocalDate.now()).atTime(LocalTime.MAX);

        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {} - Retrieve Transactions, Date Range: {} to {}", merchantId, startDate, endDate);
                    return transactionService.getAll(merchantId, startDate, endDate)
                            .doOnSuccess(list -> log.info("Merchant ID: {} - Retrieved Transactions: {}", merchantId, list));
                });
    }

    @GetMapping("/{transactionId}/details")
    public Mono<TransactionDto> getById(@PathVariable UUID transactionId,
                                        @RequestHeader("Authorization") String authHeader) {
        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {}, Retrieve Transaction By Id: {}", merchantId, transactionId);
                    return transactionService.getById(merchantId, transactionId)
                            .doOnSuccess(list -> log.info("Merchant ID: {} - Retrieved Transaction: {}", merchantId, list));
                });
    }
}
