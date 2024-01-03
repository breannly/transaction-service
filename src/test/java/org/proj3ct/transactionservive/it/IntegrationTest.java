package org.proj3ct.transactionservive.it;

import org.junit.jupiter.api.Test;
import org.proj3ct.transactionservive.dto.payout.PayoutNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionNewDto;
import org.proj3ct.transactionservive.dto.transaction.TransactionShortDto;
import org.proj3ct.transactionservive.utils.TestDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class IntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient rest;

    @Test
    void postTransaction() {
        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();

        rest.post().uri("/api/v1/payments/transaction")
                .bodyValue(transactionNewDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getTransaction() {
        TransactionNewDto transactionNewDto = TestDataUtils.createTransactionNewDto();

        TransactionShortDto transactionShortDto = rest.post().uri("/api/v1/payments/transaction")
                .bodyValue(transactionNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionShortDto.class)
                .returnResult()
                .getResponseBody();

        assert transactionShortDto != null;

        rest.get().uri("/api/v1/payments/transaction/" + transactionShortDto.getExternalTransactionId() + "/details")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void postPayout() {
        PayoutNewDto payoutNewDto = TestDataUtils.createPayoutNewDto();

        rest.post().uri("/api/v1/payments/payout")
                .bodyValue(payoutNewDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void getPayout() {
        PayoutNewDto payoutNewDto = TestDataUtils.createPayoutNewDto();

        TransactionShortDto transactionShortDto = rest.post().uri("/api/v1/payments/payout")
                .bodyValue(payoutNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionShortDto.class)
                .returnResult()
                .getResponseBody();

        assert transactionShortDto != null;

        rest.get().uri("/api/v1/payments/payout/" + transactionShortDto.getExternalTransactionId() + "/details")
                .exchange()
                .expectStatus().isOk();
    }
}
