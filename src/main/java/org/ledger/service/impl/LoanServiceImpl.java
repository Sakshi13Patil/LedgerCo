package org.ledger.service.impl;

import org.ledger.dto.PaymentDto;
import org.ledger.exception.LedgerException;
import org.ledger.model.Borrower;
import org.ledger.repository.LedgerRepository;
import org.ledger.service.LoanService;
import org.ledger.util.LoanUtil;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.ledger.util.LoanUtil.getEMIAmount;
import static org.ledger.util.LoanUtil.getUnpaidEMIS;

public class LoanServiceImpl implements LoanService {

    private final LedgerRepository ledgerRepository;

    public LoanServiceImpl(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public void grantLoan(String bankName, String borrowerName, int principal, int years, int rateOfInterest) {
        double interest = (double) principal * years * rateOfInterest/100;
        Borrower borrower = Borrower.builder()
                .id(UUID.randomUUID().toString())
                .name(borrowerName)
                .interest(rateOfInterest)
                .principal(principal)
                .numberOfYears(years)
                .EMIAmount(getEMIAmount(principal, interest, years))
                .expectedRepayAmount(principal + interest)
                .emiNoAndLumpSumMapping(new HashMap<>())
                .build();
        ledgerRepository.addBorrower(bankName, borrower);
    }

    @Override
    public void payLumpSum(String bankName, String borrowerName, int payment, int paidAfterEMINo) {
        ledgerRepository.addLumpSum(bankName, borrowerName, payment, paidAfterEMINo);
    }


    @Override
    public void fetchPaymentInfo(String bankName, String borrowerName, int emiNo) {
        Optional<Borrower> borrowerOptional = ledgerRepository.getBorrower(bankName, borrowerName);
        if (borrowerOptional.isPresent()) {
            AtomicReference<Double> amountPaid = new AtomicReference<>(emiNo * borrowerOptional.get().getEMIAmount());
            borrowerOptional.get().getEmiNoAndLumpSumMapping().forEach((k, v) -> {
                if (k <= emiNo)
                    amountPaid.updateAndGet(v1 -> v1 + v);

            });
            PaymentDto paymentDto = PaymentDto.builder()
                    .bankName(bankName)
                    .userName(borrowerName)
                    .paidAmount((int) (double) amountPaid.get())
                    .unpaidEMIs(getUnpaidEMIS(borrowerOptional.get().getExpectedRepayAmount() - amountPaid.get(),
                            borrowerOptional.get().getEMIAmount()))
                    .build();
            LoanUtil.printBalance(paymentDto);
        } else
            throw new LedgerException("Borrower " + borrowerName + " doesn't exist in bank " + bankName);
    }

}
