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

    private final PayoutManager payoutManager;
    private final PayoutRepository payoutRepository;
    private final TransactionManager transactionManager;
    private final TransactionRepository transactionRepository;
    private final WebhookManager webhookManager;

    @Override
    @Scheduled(cron = "0 */15 * * * *")
    public void processTransactions() {
        transactionRepository.getAllByStatus(TransactionStatus.IN_PROCESS.name())
                .map(transactionManager::manage)
                .flatMap(transactionRepository::save)
                .flatMap(webhookManager::sendWebhook);
    }

    @Override
    @Scheduled(cron = "0 */15 * * * *")
    public void processPayouts() {
        payoutRepository.getAllByStatus(PayoutStatus.IN_PROGRESS.name())
                .map(payoutManager::manage)
                .flatMap(payoutRepository::save)
                .flatMap(webhookManager::sendWebhook)
                .subscribe();
    }
}
