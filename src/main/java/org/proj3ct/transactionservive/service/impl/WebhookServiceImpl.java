package org.proj3ct.transactionservive.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.manager.PayoutManager;
import org.proj3ct.transactionservive.manager.TransactionManager;
import org.proj3ct.transactionservive.manager.WebhookManager;
import org.proj3ct.transactionservive.repository.PayoutRepository;
import org.proj3ct.transactionservive.repository.TransactionRepository;
import org.proj3ct.transactionservive.service.WebhookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final PayoutRepository payoutRepository;
    private final TransactionRepository transactionRepository;
    private final PayoutManager payoutManager;
    private final TransactionManager transactionManager;
    private final WebhookManager webhookManager;

    @Override
    @Scheduled(cron = "33 */1 * * * *")
    public void processTransactions() {
        log.info("Starting transaction processing");
        transactionRepository.findAllByStatus(TransactionStatus.IN_PROCESS.name())
                .flatMap(transaction -> {
                    log.info("Processing transaction: {}", transaction.getId());
                    return transactionManager.manage(transaction);
                })
                .flatMap(webhookManager::sendWebhook)
                .doOnError(error -> log.error("Error processing payouts: {}", error.getMessage()))
                .doOnComplete(() -> log.info("Completed transaction processing"))
                .subscribe();
    }

    @Override
    @Scheduled(cron = "27 */2 * * * *")
    public void processPayouts() {
        log.info("Starting payout processing");

        payoutRepository.findAllByStatus(PayoutStatus.IN_PROGRESS.name())
                .flatMap(payout -> {
                    log.info("Processing payout: {}", payout.getId());
                    return payoutManager.manage(payout);
                })
                .flatMap(webhookManager::sendWebhook)
                .doOnError(error -> log.error("Error processing payouts: {}", error.getMessage()))
                .doOnComplete(() -> log.info("Completed payout processing"))
                .subscribe();
    }
}
