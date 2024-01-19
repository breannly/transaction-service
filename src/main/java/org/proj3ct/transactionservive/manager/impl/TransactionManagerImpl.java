package org.proj3ct.transactionservive.manager.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.entity.wallet.Wallet;
import org.proj3ct.transactionservive.manager.TransactionManager;
import org.proj3ct.transactionservive.repository.TransactionRepository;
import org.proj3ct.transactionservive.repository.WalletRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionManagerImpl implements TransactionManager {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    private static final String[] FAILED_TRANSACTION_MESSAGES = {
            "PAYMENT_METHOD_NOT_ALLOWED",
            "INSUFFICIENT_FUNDS",
            "CARD_EXPIRED",
            "NETWORK_ERROR",
            "INVALID_CREDENTIALS",
            "UNAUTHORIZED_ACCESS",
            "CURRENCY_MISMATCH",
            "LIMIT_EXCEEDED",
            "TRANSACTION_DECLINED",
            "SERVICE_UNAVAILABLE"
    };

    @Override
    public Mono<Transaction> manage(Transaction transaction) {
        return walletRepository.findWalletByMerchantIdAndCurrency(transaction.getMerchantId(), transaction.getCurrency())
                .defaultIfEmpty(Wallet.builder()
                        .merchantId(transaction.getMerchantId())
                        .currency(transaction.getCurrency())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                .flatMap(wallet -> processTransaction(transaction, wallet))
                .flatMap(transactionRepository::save);
    }

    private Mono<Transaction> processTransaction(Transaction transaction, Wallet wallet) {
        TransactionStatus status = generateTransactionStatus();
        if (status.equals(TransactionStatus.APPROVED)) {
            return processCompletedTransaction(transaction, wallet);
        } else {
            return Mono.just(setTransactionFailed(transaction, generateFailedMessage()));
        }
    }

    private Mono<Transaction> processCompletedTransaction(Transaction transaction, Wallet wallet) {
        wallet.setBalance(Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO).add(transaction.getAmount()));
        wallet.setUpdatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.APPROVED);
        transaction.setMessage("OK");
        return walletRepository.save(wallet).thenReturn(transaction);
    }

    private Transaction setTransactionFailed(Transaction transaction, String message) {
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setMessage(message);
        return transaction;
    }

    private TransactionStatus generateTransactionStatus() {
        return Math.random() < 0.8 ? TransactionStatus.APPROVED : TransactionStatus.FAILED;
    }

    private String generateFailedMessage() {
        int randomIndex = (int) (Math.random() * FAILED_TRANSACTION_MESSAGES.length);
        return FAILED_TRANSACTION_MESSAGES[randomIndex];
    }
}
