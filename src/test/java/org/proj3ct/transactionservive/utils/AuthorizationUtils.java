package org.proj3ct.transactionservive.utils;

import org.proj3ct.transactionservive.entity.merchant.Merchant;

import java.util.Base64;

public class AuthorizationUtils {

    public static String getBasicAuthBy(Merchant merchant) {
        String auth = merchant.getId() + ":" + merchant.getSecretKey();
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
