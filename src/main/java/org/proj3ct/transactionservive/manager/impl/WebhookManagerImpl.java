package org.proj3ct.transactionservive.manager.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.dto.webhook.WebhookDto;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.webhook.Webhook;
import org.proj3ct.transactionservive.entity.webhook.WebhookType;
import org.proj3ct.transactionservive.manager.WebhookManager;
import org.proj3ct.transactionservive.mapper.WebhookMapper;
import org.proj3ct.transactionservive.repository.WebhookRepository;
import org.proj3ct.transactionservive.utils.WebClientUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebhookManagerImpl implements WebhookManager {

    private final WebhookMapper webhookMapper;
    private final WebhookRepository webhookRepository;
    private final WebClient webClient;

    @Override
    public Mono<Webhook> sendWebhook(Transaction transaction) {
        Webhook webhook = webhookMapper.map(transaction).toBuilder()
                .isNew(true)
                .id(UUID.randomUUID())
                .type(WebhookType.TRANSACTION)
                .build();
        return processWebhook(transaction.getNotificationUrl(), webhook);
    }

    @Override
    public Mono<Webhook> sendWebhook(Payout payout) {
        Webhook webhook = webhookMapper.map(payout).toBuilder()
                .isNew(true)
                .id(UUID.randomUUID())
                .type(WebhookType.PAYOUT)
                .build();
        return processWebhook(payout.getNotificationUrl(), webhook);
    }

    private Mono<Webhook> processWebhook(String url, Webhook webhook) {
        WebhookDto webhookDto = webhookMapper.map(webhook);
        return webClient.post()
                .uri(url)
                .bodyValue(webhookDto)
                .retrieve()
                .toBodilessEntity()
                .retryWhen(Retry.fixedDelay(3, Duration.ofMinutes(10))
                        .filter(WebClientUtils::isError)
                        .doAfterRetry(retrySignal -> {
                            incrementWebhook(webhook)
                                    .subscribe(updatedWebhook ->
                                            webhook.setCount(updatedWebhook.getCount()));
                        })
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> new RuntimeException(
                                "Send webhook failed after max retries")
                        )))
                .flatMap(response -> incrementWebhook(webhook));
    }

    private Mono<Webhook> incrementWebhook(Webhook webhook) {
        if (webhook.getCount() != 0) {
            webhook = webhook.toBuilder()
                    .isNew(false)
                    .build();
        }

        Webhook updatedWebhook = webhook.toBuilder()
                .updatedAt(LocalDateTime.now())
                .count(webhook.getCount() + 1)
                .build();

        return webhookRepository.save(updatedWebhook);
    }
}
