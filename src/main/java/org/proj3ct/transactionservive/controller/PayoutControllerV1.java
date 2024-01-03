package org.proj3ct.transactionservive.controller;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.dto.payout.PayoutDto;
import org.proj3ct.transactionservive.dto.payout.PayoutListDto;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.payout.PayoutShortDto;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.mapper.PayoutMapper;
import org.proj3ct.transactionservive.service.PayoutService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments/payout")
public class PayoutControllerV1 {

    private final PayoutMapper payoutMapper;
    private final PayoutService payoutService;

    @PostMapping
    public Mono<PayoutShortDto> save(@RequestBody @Validated PayoutNewDto payoutNewDto) {
        Payout payout = payoutMapper.map(payoutNewDto);
        return payoutService.save(payout)
                .map(payoutMapper::mapShort);
    }

    @GetMapping("/list")
    public Mono<PayoutListDto> getAll(@RequestParam(required = false) String start,
                                      @RequestParam(required = false) String end) {
        LocalDateTime startDate = (start != null ? LocalDate.parse(start) : LocalDate.now()).atStartOfDay();
        LocalDateTime endDate = (start != null ? LocalDate.parse(end) : LocalDate.now()).atTime(LocalTime.MAX);

        return payoutService.getAll(startDate, endDate)
                .map(payoutMapper::map).collectList()
                .map(PayoutListDto::of);
    }

    @GetMapping("/{payoutId}/details")
    public Mono<PayoutDto> getById(@PathVariable Long payoutId) {
        return payoutService.getById(payoutId)
                .map(payoutMapper::map);
    }
}
