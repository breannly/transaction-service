package org.proj3ct.transactionservive.dto.transaction;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionNewDto {

    @NotNull
    private String paymentMethod;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String currency;
    @NotNull
    private CardData cardData;
    @NotNull
    private String language;
    @NotNull
    private String notificationUrl;
    @NotNull
    private Customer customer;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;

}
