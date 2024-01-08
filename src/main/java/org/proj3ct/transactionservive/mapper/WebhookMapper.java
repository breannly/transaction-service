package org.proj3ct.transactionservive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.proj3ct.transactionservive.dto.common.CardDataDto;
import org.proj3ct.transactionservive.dto.webhook.WebhookDto;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.webhook.Webhook;
import org.proj3ct.transactionservive.utils.MaskUtils;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WebhookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionId", source = "id")
    Webhook map(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactionId", source = "id")
    Webhook map(Payout payout);

    WebhookDto map(Webhook webhook);

    default CardDataDto map(CardData cardData) {
       CardDataDto cardDataDto = new CardDataDto();
       cardDataDto.setCardNumber(MaskUtils.maskCardNumber(cardData.getCardNumber()));
       return cardDataDto;
    }
}
