package org.ledger.exception;

public class LedgerException extends RuntimeException {
    public LedgerException() {
        super();
    }

    public LedgerException(String message) {
        super(message);
    }
}
