package com.example.financedashboardpro.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "plaid_items")
public class PlaidItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String itemId;

    @Column(nullable = false)
    private String accessToken;

    private String cursor;
}
