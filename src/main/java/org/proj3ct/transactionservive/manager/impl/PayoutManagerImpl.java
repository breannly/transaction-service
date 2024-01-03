package org.proj3ct.transactionservive.manager.impl;

import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.manager.PayoutManager;
import org.springframework.stereotype.Component;

@Component
public class PayoutManagerImpl implements PayoutManager {

    private static final String[] FAILED_PAYOUT_MESSAGES = {
            "BENEFICIARY_ACCOUNT_INVALID",
            "PAYOUT_AMOUNT_EXCEEDS_LIMIT",
            "INSUFFICIENT_BALANCE",
            "BENEFICIARY_ACCOUNT_FROZEN",
            "CURRENCY_CONVERSION_NOT_AVAILABLE",
            "PAYOUT_METHOD_UNAVAILABLE",
            "NETWORK_ISSUE",
            "AUTHENTICATION_FAILED",
            "UNAUTHORIZED_OPERATION",
            "SERVICE_UNAVAILABLE_FOR_PAYOUT"
    };

    @Override
    public Payout manage(Payout payout) {
        PayoutStatus status = generatePayoutStatus();
        String message = generateMessageByStatus(status);
        return payout.toBuilder()
                .status(status)
                .message(message)
                .build();
    }

    private PayoutStatus generatePayoutStatus() {
        return Math.random() < 0.8 ? PayoutStatus.COMPLETED : PayoutStatus.FAILED;
    }

    private String generateMessageByStatus(PayoutStatus status) {
        if (status.equals(PayoutStatus.COMPLETED)) {
            return "Payout is successfully completed";
        }
        int randomIndex = (int) (Math.random() * FAILED_PAYOUT_MESSAGES.length);
        return FAILED_PAYOUT_MESSAGES[randomIndex];
    }
}
