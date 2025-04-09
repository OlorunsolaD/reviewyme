package com.besttime.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    private String id;

    private String userId;
    private String productName;
    private Double amount;
    private Integer quantity;
    private String currency;
    private String status;
    private String uniqueTransactionId;
    private String referenceId;

}
