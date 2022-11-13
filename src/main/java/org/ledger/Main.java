package org.ledger;

import org.ledger.repository.LedgerRepository;
import org.ledger.service.LoanService;
import org.ledger.service.impl.LoanServiceImpl;

import java.io.*;

public class Main {
    static LedgerRepository ledgerRepository = new LedgerRepository();
    static LoanService loanService = new LoanServiceImpl(ledgerRepository);

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        File file = new File(
                "src/main/resources/input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] inputArray = st.split(" ");

            //LOAN BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST
            if ("LOAN".equals(inputArray[0]))
                loanService.grantLoan(inputArray[1], inputArray[2],
                        Integer.parseInt(inputArray[3]),
                        Integer.parseInt(inputArray[4]),
                        Integer.parseInt(inputArray[5]));

                //PAYMENT BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO
            else if ("PAYMENT".equals(inputArray[0]))
                loanService.payLumpSum(inputArray[1],
                        inputArray[2],
                        Integer.parseInt(inputArray[3]),
                        Integer.parseInt(inputArray[4]));

                //BALANCE BANK_NAME BORROWER_NAME EMI_NO
            else if ("BALANCE".equals(inputArray[0]))
                loanService.fetchPaymentInfo(inputArray[1],
                        inputArray[2],
                        Integer.parseInt(inputArray[3]));
            else
                System.out.println("Command Not Found");
        }
    }
}