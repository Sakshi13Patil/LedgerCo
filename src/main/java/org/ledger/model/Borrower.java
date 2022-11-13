package org.ledger.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.Map;

@Data
@Builder
public class Borrower {

    @Id
    private String id;
    private String name;
    private double interest;
    private double principal;
    private int numberOfYears;
    private double EMIAmount;
    private double expectedRepayAmount;
    private Map<Integer,Integer> emiNoAndLumpSumMapping;

}
