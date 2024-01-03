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

@Component
@RequiredArgsConstructor
public class WebhookManagerImpl implements WebhookManager {

    private final WebhookMapper webhookMapper;
    private final WebhookRepository webhookRepository;
    private final WebClient webClient;

    @Override
    public Mono<Void> sendNotification(Transaction transaction) {
        Webhook webhook = webhookMapper.map(transaction);
        webhook.setType(WebhookType.TRANSACTION);
        return sendWebhook(transaction.getNotificationUrl(), webhook);
    }

    @Override
    public Mono<Void> sendNotification(Payout payout) {
        Webhook webhook = webhookMapper.map(payout);
        webhook.setType(WebhookType.PAYOUT);
        return sendWebhook(payout.getNotificationUrl(), webhook);
    }

    private Mono<Void> sendWebhook(String url, Webhook webhook) {
        WebhookDto webhookDto = webhookMapper.map(webhook);
        return webClient.post()
                .uri(url)
                .bodyValue(webhookDto)
                .retrieve()
                .toBodilessEntity()
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(WebClientUtils::isError)
                        .doAfterRetry(retrySignal -> updateWebhook(webhook).subscribe())
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> new RuntimeException(
                                "Send webhook failed after max retries")
                        )))
                .flatMap(response -> updateWebhook(webhook));
    }

    private Mono<Void> updateWebhook(Webhook webhook) {
        return webhookRepository.save(
                        webhook.toBuilder()
                                .id(null)
                                .updatedAt(LocalDateTime.now())
                                .build()
                )
                .then();
    }
}
