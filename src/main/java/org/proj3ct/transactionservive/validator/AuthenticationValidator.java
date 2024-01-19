package org.proj3ct.transactionservive.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.proj3ct.transactionservive.repository.MerchantRepository;
import org.proj3ct.transactionservive.utils.CredentialsUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationValidator {

    private final MerchantRepository merchantRepository;

    public Mono<ValidationResult> validate(String authHeader) {
        if (!isValidAuthHeader(authHeader)) {
            return Mono.just(ValidationResult.error("INVALID_HEADER"));
        }

        try {
            Pair<Long, String> credentials = CredentialsUtils.parseCredentials(authHeader);
            return validateCredentials(credentials);
        } catch (IllegalArgumentException e) {
            return Mono.just(ValidationResult.error("INVALID_CREDENTIALS"));
        }

    }

    private boolean isValidAuthHeader(String authHeader) {
        return authHeader != null && authHeader.startsWith("Basic ");
    }

    private Mono<ValidationResult> validateCredentials(Pair<Long, String> credentials) {
        return merchantRepository.findById(credentials.getFirst())
                .flatMap(merchant -> merchant.getSecretKey().equals(credentials.getSecond())
                        ? Mono.just(ValidationResult.success())
                        : Mono.just(ValidationResult.error("INCORRECT_CREDENTIALS"))
                .defaultIfEmpty(ValidationResult.error("MERCHANT_NOT_FOUND")));
    }
}
