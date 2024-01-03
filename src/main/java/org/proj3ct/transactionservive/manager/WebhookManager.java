package org.proj3ct.transactionservive.manager;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface WebhookManager {

    Mono<Void> sendNotification(Transaction transaction);

    Mono<Void> sendNotification(Payout payout);
}
