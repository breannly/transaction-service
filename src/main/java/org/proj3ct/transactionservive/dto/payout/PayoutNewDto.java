package org.proj3ct.transactionservive.dto.payout;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayoutNewDto {

    @NotNull
    private String paymentMethod;
    @NotNull
    private Integer amount;
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
}
