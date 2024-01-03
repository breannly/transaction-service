package org.proj3ct.transactionservive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.proj3ct.transactionservive.dto.common.CardDataDto;
import org.proj3ct.transactionservive.dto.common.CustomerDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.utils.JsonUtils;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "cardDataJson", source = "cardData")
    @Mapping(target = "customerJson", source = "customer")
    Transaction map(TransactionNewDto transactionNewDto);

    default String map(CardData cardData) {
        return JsonUtils.writeValueAsString(cardData);
    }

    default String map(Customer customer) {
        return JsonUtils.writeValueAsString(customer);
    }

    @Mapping(target = "externalTransactionId", source = "id")
    TransactionShortDto mapShort(Transaction transaction);

    @Mapping(target = "externalTransactionId", source = "id")
    @Mapping(target = "cardData", source = "cardDataJson")
    @Mapping(target = "customer", source = "customerJson")
    TransactionDto map(Transaction transaction);

    default CustomerDto mapCustomer(String json) {
        Customer customer = JsonUtils.readValue(json, Customer.class);
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    default CardDataDto mapCardData(String json) {
        CardData cardData = JsonUtils.readValue(json, CardData.class);
        CardDataDto cardDataDto = new CardDataDto();
        cardDataDto.setCardNumber(cardData.getCardNumber());
        return cardDataDto;
    }
}
