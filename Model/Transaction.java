package com.example.financedashboardpro.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String plaidTransactionId;

    @Column(nullable = false)
    private Long userId;

    private double amount;
    private LocalDate date;
    private String merchantName;
    private String category;
}
