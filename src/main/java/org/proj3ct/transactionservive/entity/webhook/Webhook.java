package org.proj3ct.transactionservive.entity.webhook;

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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("webhooks")
public class Webhook {

    @Id
    private Long id;
    private String paymentMethod;
    private String amount;
    private String currency;
    private WebhookType type;
    private Long transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ToString.Exclude
    private String cardDataJson;
    @Transient
    private CardData cardData;
    private String language;
    @ToString.Exclude
    private String customerJson;
    @Transient
    private Customer customer;
    private String status;
    private String message;
}
