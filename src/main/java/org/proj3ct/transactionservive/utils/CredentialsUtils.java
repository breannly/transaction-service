package org.proj3ct.transactionservive.utils;

import org.springframework.data.util.Pair;

import java.util.Base64;
import java.util.StringTokenizer;

public final class CredentialsUtils {

    private CredentialsUtils() {
    }

    public static Pair<Long, String> parseCredentials(String authHeader) {
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));

        StringTokenizer tokenizer = new StringTokenizer(decodedCredentials, ":");
        Long merchantId = Long.parseLong(tokenizer.nextToken());
        String password = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        return Pair.of(merchantId, password);
    }
}
