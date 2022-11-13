package org.ledger.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
public class Bank {
    @Id
    private String id;
    private String bankName;
    private List<Borrower> borrowerList;

}
