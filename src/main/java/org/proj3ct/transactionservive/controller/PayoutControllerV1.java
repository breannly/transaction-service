package org.proj3ct.transactionservive.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.proj3ct.transactionservive.dto.payout.PayoutDto;
import org.proj3ct.transactionservive.dto.payout.PayoutListDto;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.payout.PayoutShortDto;
import org.proj3ct.transactionservive.service.PayoutService;
import org.proj3ct.transactionservive.utils.CredentialsUtils;
import org.proj3ct.transactionservive.validator.AuthorizationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/payout")
public class PayoutControllerV1 {

    private final AuthorizationValidator authenticationValidator;
    private final PayoutService payoutService;

    @PostMapping
    public Mono<PayoutShortDto> save(@RequestBody @Validated PayoutNewDto payoutNewDto,
                                     @RequestHeader("Authorization") String authHeader) {
        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {} - Payout Save Attempt: {}", merchantId, payoutNewDto);
                    return payoutService.save(merchantId, payoutNewDto)
                            .doOnSuccess(response -> log.info("Merchant ID: {} - Saved Payout: {}", merchantId, response));
                });
    }

    @GetMapping("/list")
    public Mono<PayoutListDto> getAll(@RequestParam(required = false) String start,
                                      @RequestParam(required = false) String end,
                                      @RequestHeader("Authorization") String authHeader) {
        LocalDateTime startDate = (start != null ? LocalDate.parse(start) : LocalDate.now()).atStartOfDay();
        LocalDateTime endDate = (start != null ? LocalDate.parse(end) : LocalDate.now()).atTime(LocalTime.MAX);

        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {} - Retrieve Payout, Date Range: {} to {}", merchantId, startDate, endDate);
                    return payoutService.getAll(merchantId, startDate, endDate)
                            .doOnSuccess(list -> log.info("Merchant ID: {} - Retrieved Payout: {}", merchantId, list));
                });
    }

    @GetMapping("/{payoutId}/details")
    public Mono<PayoutDto> getById(@PathVariable UUID payoutId,
                                   @RequestHeader("Authorization") String authHeader) {
        return authenticationValidator.validate(authHeader)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
                    }
                    Long merchantId = CredentialsUtils.parseCredentials(authHeader).getFirst();
                    log.info("Merchant ID: {}, Retrieve Payout By Id: {}", merchantId, payoutId);
                    return payoutService.getById(merchantId, payoutId)
                            .doOnSuccess(list -> log.info("Merchant ID: {} - Retrieved Payout: {}", merchantId, list));
                });
    }
}
