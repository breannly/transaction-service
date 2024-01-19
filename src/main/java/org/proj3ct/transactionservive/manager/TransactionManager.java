package org.proj3ct.transactionservive.manager;

import org.proj3ct.transactionservive.entity.transaction.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionManager {

    Mono<Transaction> manage(Transaction transaction);
}
