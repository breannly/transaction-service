package org.proj3ct.transactionservive.manager;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.webhook.Webhook;
import reactor.core.publisher.Mono;

public interface WebhookManager {

    Mono<Webhook> sendWebhook(Transaction transaction);

    Mono<Webhook> sendWebhook(Payout payout);
}
