package org.proj3ct.transactionservive.entity.payout;

import lombok.*;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("payouts")
public class Payout {

    @Id
    private Long id;
    private String paymentMethod;
    private Integer amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ToString.Exclude
    private String cardDataJson;
    @Transient
    private CardData cardData;
    private String language;
    private String notificationUrl;
    @ToString.Exclude
    private String customerJson;
    @Transient
    private Customer customer;
    private PayoutStatus status;
    private String message;
}
