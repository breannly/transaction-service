package org.proj3ct.transactionservive.utils;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public final class WebClientUtils {

    private WebClientUtils() {
    }

    public static boolean isError(Throwable throwable) {
        return throwable instanceof WebClientResponseException responseException
                && (responseException.getStatusCode().is5xxServerError()
                || responseException.getStatusCode().is4xxClientError());
    }
}
