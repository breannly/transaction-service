package org.proj3ct.transactionservive.entity.webhook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table("webhooks")
public class Webhook implements Persistable<UUID> {

    @Id
    private UUID id;
    private String paymentMethod;
    private String amount;
    private String currency;
    private WebhookType type;
    private UUID transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CardData cardData;
    private String language;
    private Customer customer;
    private int count;
    private String status;
    private String message;

    @Transient
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew || id == null;
    }
}
