package org.proj3ct.transactionservive.manager;

import org.proj3ct.transactionservive.entity.payout.Payout;
import reactor.core.publisher.Mono;

public interface PayoutManager {

    Mono<Payout> manage(Payout payout);
}
