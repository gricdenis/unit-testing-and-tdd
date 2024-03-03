package com.acme.banking.dbo.exception;

public class TransferAccountAmountException extends RuntimeException{

    public TransferAccountAmountException(String message) {
        super(message);
    }
}
