package org.proj3ct.transactionservive.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.proj3ct.transactionservive.dto.common.CardDataDto;
import org.proj3ct.transactionservive.dto.common.CustomerDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayoutDto {

    private UUID externalTransactionId;
    private String paymentMethod;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notificationUrl;
    private CardDataDto cardData;
    private String language;
    private CustomerDto customer;
    private String status;
    private String message;
}
