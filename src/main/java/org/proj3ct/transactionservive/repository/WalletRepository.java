package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.wallet.Wallet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface WalletRepository extends R2dbcRepository<Wallet, Long> {

    Mono<Wallet> findWalletByMerchantIdAndCurrency(Long merchantId, String currency);
}
