package org.ledger.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {
    private String bankName;
    private String userName;
    private int paidAmount;
    private int unpaidEMIs;
}
