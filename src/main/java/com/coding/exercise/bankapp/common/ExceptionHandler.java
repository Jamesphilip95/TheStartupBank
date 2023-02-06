package com.coding.exercise.bankapp.common;

public class ExceptionHandler {

    public static void validateSourceAccount(String sourceAccountNumber, Boolean present) {
        if(!present) {
            buildBadRequestException(sourceAccountNumber, "source account");
        }
    }

    public static void validateTargetAccount(String targetAccountNumber, Boolean present) {
        if(!present) {
            buildBadRequestException(targetAccountNumber, "target account");
        }
    }

    private static void buildBadRequestException(String resourceNumber, String type) {
        String exceptionMessage = "No " + type + " with " + getTypeString(type) + " number " + resourceNumber;
        throw new BadRequestException(exceptionMessage);
    }

    private static String getTypeString(String type) {
        return type.contains(" ") ? type.split(" ")[1] : type;
    }

    public static void validateTransactionFound(String transactionId, boolean present) {
        if(!present) {
            buildNotFoundException(transactionId, "transaction");
        }
    }

    private static void buildNotFoundException(String number, String type) {
        String exceptionMessage = "No " + type + " with " + type + " number " + number;
        throw new ResourceNotFoundException(exceptionMessage);
    }

    public static void validateCustomerFound(Long customerNumber, boolean present) {
        if(!present) {
            buildNotFoundException(customerNumber.toString(), "customer");
        }
    }

    public static void validateAccountResourceFound(String accountNumber, boolean present) {
        if(!present) {
            buildNotFoundException(accountNumber, "account");
        }
    }

    public static void validateCustomer(Long customerNumber, boolean present) {
        if(!present) {
            buildBadRequestException(customerNumber.toString(), "customer");
        }
    }
}
