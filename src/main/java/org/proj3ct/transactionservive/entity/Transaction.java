package org.proj3ct.transactionservive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Transaction {

    @JsonProperty("external_transaction_id")
    private Long id;
    private String paymentMethod;
    private Integer amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ToString.Exclude
    private String cardDataJson;
    private CardData cardData;
    private String language;
    private String notificationUrl;
    @ToString.Exclude
    private String customerJson;
    private Customer customer;

}
