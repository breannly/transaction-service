package org.proj3ct.transactionservive.validator;

import lombok.Getter;

public class ValidationResult {

    @Getter
    private final ValidationResult.Status status;
    private final String error;

    public ValidationResult(ValidationResult.Status status, String error) {
        this.status = status;
        this.error = error;
    }

    public static ValidationResult success() {
        return new ValidationResult(ValidationResult.Status.SUCCESS, "");
    }

    public static ValidationResult error(String error) {
        return new ValidationResult(ValidationResult.Status.ERROR, error);
    }

    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }

    public boolean isError() {
        return this.status == Status.ERROR;
    }

    public static enum Status {
        SUCCESS,
        ERROR;

        private Status() {

        }
    }
}
