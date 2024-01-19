package org.proj3ct.transactionservive.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.proj3ct.transactionservive.entity.common.CardData;
import org.proj3ct.transactionservive.entity.common.Customer;
import org.proj3ct.transactionservive.entity.merchant.Merchant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("transactions")
public class Transaction implements Persistable<UUID> {

    @Id
    private UUID id;
    private Long merchantId;
    @Transient
    private Merchant merchant;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CardData cardData;
    private String language;
    private String notificationUrl;
    private Customer customer;
    private TransactionStatus status;
    private String message;

    @Transient
    private boolean isNew;

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew || id == null;
    }
}
