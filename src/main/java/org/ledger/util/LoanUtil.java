package org.ledger.util;

import org.ledger.dto.PaymentDto;

public class LoanUtil {
    public static double getEMIAmount(double principal, double interest, int noOfYears) {
        return Math.ceil((principal+interest)/(noOfYears*12));
    }

    public static int getUnpaidEMIS(double unPaidAmount, double emiAmount) {
        return (int) Math.ceil(unPaidAmount/emiAmount);
    }

    public static void printBalance(PaymentDto paymentDto) {
        System.out.println(paymentDto.getBankName() + " " + paymentDto.getUserName() + " " + paymentDto.getPaidAmount() + " " + paymentDto.getUnpaidEMIs());
    }
}