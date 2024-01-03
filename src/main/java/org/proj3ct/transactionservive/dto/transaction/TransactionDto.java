package org.proj3ct.transactionservive.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.proj3ct.transactionservive.dto.common.CardDataDto;
import org.proj3ct.transactionservive.dto.common.CustomerDto;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionDto {

    private Long externalTransactionId;
    private String paymentMethod;
    private String amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notificationUrl;
    private CardDataDto cardData;
    private String language;
    private CustomerDto customer;
    private String status;
    private String message;
}