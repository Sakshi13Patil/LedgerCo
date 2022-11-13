package org.ledger.service;

public interface LoanService {

    void grantLoan(String bankName, String borrowerName ,int principal, int years, int rateOfInterest);

    void payLumpSum(String bankName, String borrowerName, int payment, int paidAfterEMINo);

    void fetchPaymentInfo(String bankName, String borrowerName, int emiNo);
}
