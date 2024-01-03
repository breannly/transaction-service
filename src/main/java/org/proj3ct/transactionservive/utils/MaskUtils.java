package org.proj3ct.transactionservive.utils;

public class MaskUtils {

    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() <= 4) {
            return cardNumber;
        }

        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        String maskedSection = cardNumber.substring(0, cardNumber.length() - 4)
                .replaceAll(".", "*");

        return maskedSection + lastFourDigits;
    }
}
