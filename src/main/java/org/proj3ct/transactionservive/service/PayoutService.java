package org.proj3ct.transactionservive.service;

import org.proj3ct.transactionservive.dto.payout.PayoutDto;
import org.proj3ct.transactionservive.dto.payout.PayoutListDto;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.payout.PayoutShortDto;
import org.proj3ct.transactionservive.entity.payout.Payout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PayoutService {

    Mono<PayoutShortDto> save(Long merchantId, PayoutNewDto payout);

    Mono<PayoutListDto> getAll(Long merchantId, LocalDateTime startDate, LocalDateTime endDate);

    Mono<PayoutDto> getById(Long merchantId, UUID id);

}
