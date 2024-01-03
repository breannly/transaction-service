package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.webhook.Webhook;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookRepository extends R2dbcRepository<Webhook, Long> {
}
