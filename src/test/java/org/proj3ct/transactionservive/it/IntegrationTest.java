package org.proj3ct.transactionservive.it;

import org.junit.jupiter.api.Test;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.entity.merchant.Merchant;
import org.proj3ct.transactionservive.repository.MerchantRepository;
import org.proj3ct.transactionservive.utils.AuthorizationUtils;
import org.proj3ct.transactionservive.utils.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class IntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient rest;
    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    void postTransaction() {
        Merchant merchant = TestDataUtils.createMerchant();
        merchantRepository.save(merchant).block();

        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();

        rest.post().uri("/api/v1/payments/transaction")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .bodyValue(transactionNewDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getTransaction() {
        Merchant merchant = TestDataUtils.createMerchant();
        merchantRepository.save(merchant).block();

        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();

        TransactionShortDto transactionShortDto = rest.post().uri("/api/v1/payments/transaction")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .bodyValue(transactionNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionShortDto.class)
                .returnResult()
                .getResponseBody();

        assert transactionShortDto != null;

        rest.get().uri("/api/v1/payments/transaction/" + transactionShortDto.getExternalTransactionId() + "/details")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void postPayout() {
        Merchant merchant = TestDataUtils.createMerchant();
        merchantRepository.save(merchant).block();

        PayoutNewDto payoutNewDto = TestDataUtils.createPayoutNewDto();

        rest.post().uri("/api/v1/payments/payout")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .bodyValue(payoutNewDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getPayout() {
        Merchant merchant = TestDataUtils.createMerchant();
        merchantRepository.save(merchant).block();

        PayoutNewDto payoutNewDto = TestDataUtils.createPayoutNewDto();

        TransactionShortDto transactionShortDto = rest.post().uri("/api/v1/payments/payout")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .bodyValue(payoutNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionShortDto.class)
                .returnResult()
                .getResponseBody();

        assert transactionShortDto != null;

        rest.get().uri("/api/v1/payments/payout/" + transactionShortDto.getExternalTransactionId() + "/details")
                .header("Authorization", AuthorizationUtils.getBasicAuthBy(merchant))
                .exchange()
                .expectStatus().isOk();
    }
}
