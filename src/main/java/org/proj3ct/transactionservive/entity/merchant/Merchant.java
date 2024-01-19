package org.proj3ct.transactionservive.entity.merchant;

import lombok.*;
import org.proj3ct.transactionservive.entity.wallet.Wallet;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table("merchants")
public class Merchant {

    @Id
    private Long id;
    private String secretKey;
    @Transient
    private List<Wallet> wallets;
}
