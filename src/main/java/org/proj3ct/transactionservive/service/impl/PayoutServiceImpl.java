package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.dto.payout.PayoutDto;
import org.proj3ct.transactionservive.dto.payout.PayoutListDto;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.payout.PayoutShortDto;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.mapper.PayoutMapper;
import org.proj3ct.transactionservive.repository.PayoutRepository;
import org.proj3ct.transactionservive.service.PayoutService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutMapper payoutMapper;
    private final PayoutRepository payoutRepository;

    @Override
    public Mono<PayoutShortDto> save(Long merchantId, PayoutNewDto payoutNewDto) {
        Payout payout = payoutMapper.map(payoutNewDto);
        return payoutRepository.save(
                payout.toBuilder()
                        .isNew(true)
                        .id(UUID.randomUUID())
                        .merchantId(merchantId)
                        .status(PayoutStatus.IN_PROGRESS)
                        .build())
                .map(payoutMapper::mapShort);
    }

    @Override
    public Mono<PayoutListDto> getAll(Long merchantId, LocalDateTime startDate, LocalDateTime endDate) {
        return payoutRepository.findAllByMerchantIdAndCreatedAtBetween(merchantId, startDate, endDate)
                .map(payoutMapper::map).collectList()
                .map(PayoutListDto::of);
    }

    @Override
    public Mono<PayoutDto> getById(Long merchantId, UUID id) {
        return payoutRepository.findById(id)
                .filter(payout -> payout.getMerchantId().equals(merchantId))
                .map(payoutMapper::map);
    }
}
