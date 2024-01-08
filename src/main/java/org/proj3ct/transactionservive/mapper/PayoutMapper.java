package org.proj3ct.transactionservive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.proj3ct.transactionservive.dto.payout.PayoutDto;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.payout.PayoutShortDto;
import org.proj3ct.transactionservive.entity.payout.Payout;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayoutMapper {

    Payout map(PayoutNewDto payoutNewDto);

    @Mapping(target = "externalTransactionId", source = "id")
    PayoutShortDto mapShort(Payout payout);

    @Mapping(target = "externalTransactionId", source = "id")
    PayoutDto map(Payout payout);

}
