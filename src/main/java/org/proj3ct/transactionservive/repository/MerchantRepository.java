package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.merchant.Merchant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MerchantRepository extends R2dbcRepository<Merchant, Long> {
}
