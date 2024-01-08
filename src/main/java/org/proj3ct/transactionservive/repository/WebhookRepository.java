package org.proj3ct.transactionservive.repository;

import org.proj3ct.transactionservive.entity.webhook.Webhook;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebhookRepository extends R2dbcRepository<Webhook, UUID> {
}
