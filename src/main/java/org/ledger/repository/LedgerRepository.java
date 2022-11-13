package org.ledger.repository;

import org.ledger.exception.LedgerException;
import org.ledger.model.Bank;
import org.ledger.model.Borrower;

import java.util.*;

public class LedgerRepository {
    List<Bank> bankList = new ArrayList<>();

    public Optional<Bank> findByName(String bankName) {
        return bankList.stream().filter(bank -> bank.getBankName().equals(bankName)).findFirst();
    }

    public void addBorrower(String bankName, Borrower borrower) {
        Optional<Bank> bank = findByName(bankName);
        if (bank.isPresent()) {
            bank.get().getBorrowerList().add(borrower);
        } else {
            Bank bankToBeCreated = Bank.builder()
                    .id(UUID.randomUUID().toString())
                    .bankName(bankName)
                    .borrowerList(Collections.singletonList(borrower))
                    .build();
            bankList.add(bankToBeCreated);
        }
    }

    public void addLumpSum(String bankName, String borrowerName, int payment, int paidAfterEMINo) {
        Optional<Borrower> borrowerOptional = getBorrower(bankName, borrowerName);
        if (borrowerOptional.isPresent()) {
            Borrower borrower = borrowerOptional.get();
            borrower.getEmiNoAndLumpSumMapping().put(paidAfterEMINo, payment);
        } else
            throw new LedgerException("Borrower " + borrowerName + " doesn't exist in bank " + bankName);
    }

    public Optional<Borrower> getBorrower(String bankName, String borrowerName) {
        Optional<Bank> bank = findByName(bankName);
        if (bank.isPresent()) {
            return bank.get().getBorrowerList()
                    .stream()
                    .filter(borrower -> borrower.getName().equals(borrowerName))
                    .findFirst();
        } else
            throw new LedgerException("Bank " + bankName + " doesn't exist");
    }
}
