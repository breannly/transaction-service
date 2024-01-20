package org.proj3ct.transactionservive.utils;

import org.jeasy.random.EasyRandom;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.entity.merchant.Merchant;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

public class TestDataUtils {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();


    public static TransactionNewDto createTransactionNewDto() {
        TransactionNewDto transactionNewDto = EASY_RANDOM.nextObject(TransactionNewDto.class);
        transactionNewDto.setNotificationUrl("https://webhook.site/fddb73ee-b4f8-42df-b74a-26db3c2732c8");
        return transactionNewDto;
    }

    public static PayoutNewDto createPayoutNewDto() {
        PayoutNewDto payoutNewDto = EASY_RANDOM.nextObject(PayoutNewDto.class);
        payoutNewDto.setNotificationUrl("https://webhook.site/fddb73ee-b4f8-42df-b74a-26db3c2732c8");
        return payoutNewDto;
    }

    public static Merchant createMerchant() {
        return Merchant.builder()
                .secretKey(EASY_RANDOM.nextObject(String.class))
                .build();
    }
}
