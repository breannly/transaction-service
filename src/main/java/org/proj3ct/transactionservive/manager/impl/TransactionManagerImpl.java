package org.proj3ct.transactionservive.manager.impl;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import org.proj3ct.transactionservive.entity.transaction.TransactionStatus;
import org.proj3ct.transactionservive.manager.TransactionManager;
import org.springframework.stereotype.Component;

@Component
public class TransactionManagerImpl implements TransactionManager {

    private static final String[] FAILED_MESSAGES = {
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
    public Transaction manage(Transaction transaction) {
        TransactionStatus status = generateTransactionStatus();
        String message = generateMessageByStatus(status);
        return transaction.toBuilder()
                .status(status)
                .message(message)
                .build();
    }

    private TransactionStatus generateTransactionStatus() {
        return Math.random() < 0.8 ? TransactionStatus.APPROVED : TransactionStatus.FAILED;
    }

    private String generateMessageByStatus(TransactionStatus status) {
        if (status.equals(TransactionStatus.APPROVED)) {
            return "OK";
        }
        int randomIndex = (int) (Math.random() * FAILED_MESSAGES.length);
        return FAILED_MESSAGES[randomIndex];
    }
}
