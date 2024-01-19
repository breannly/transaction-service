package org.proj3ct.transactionservive.manager.impl;

import lombok.RequiredArgsConstructor;
import org.proj3ct.transactionservive.entity.payout.Payout;
import org.proj3ct.transactionservive.entity.payout.PayoutStatus;
import org.proj3ct.transactionservive.entity.wallet.Wallet;
import org.proj3ct.transactionservive.manager.PayoutManager;
import org.proj3ct.transactionservive.repository.PayoutRepository;
import org.proj3ct.transactionservive.repository.WalletRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PayoutManagerImpl implements PayoutManager {

    private final WalletRepository walletRepository;
    private final PayoutRepository payoutRepository;

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
    public Mono<Payout> manage(Payout payout) {
        return walletRepository.findWalletByMerchantIdAndCurrency(payout.getMerchantId(), payout.getCurrency())
                .flatMap(wallet -> processPayout(payout, wallet))
                .defaultIfEmpty(setPayoutFailed(payout, "WALLET_NOT_FOUND"))
                .flatMap(payoutRepository::save);
    }

    private Mono<Payout> processPayout(Payout payout, Wallet wallet) {
        PayoutStatus status = generatePayoutStatus();
        if (status.equals(PayoutStatus.COMPLETED)) {
            return processCompletedPayout(payout, wallet);
        } else {
            return Mono.just(setPayoutFailed(payout, generateFailedMessage()));
        }
    }

    private Mono<Payout> processCompletedPayout(Payout payout, Wallet wallet) {
        if (wallet.getBalance().compareTo(payout.getAmount()) >= 0) {
            wallet.setBalance(wallet.getBalance().subtract(payout.getAmount()));
            payout.setStatus(PayoutStatus.COMPLETED);
            payout.setMessage("OK");
            return walletRepository.save(wallet).thenReturn(payout);
        } else {
            return Mono.just(setPayoutFailed(payout, "INSUFFICIENT_BALANCE"));
        }
    }

    private Payout setPayoutFailed(Payout payout, String message) {
        payout.setStatus(PayoutStatus.FAILED);
        payout.setMessage(message);
        return payout;
    }

    private PayoutStatus generatePayoutStatus() {
        return Math.random() < 0.8 ? PayoutStatus.COMPLETED : PayoutStatus.FAILED;
    }

    private String generateFailedMessage() {
        int randomIndex = (int) (Math.random() * FAILED_PAYOUT_MESSAGES.length);
        return FAILED_PAYOUT_MESSAGES[randomIndex];
    }
}
