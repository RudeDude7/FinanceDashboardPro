package com.example.financedashboardpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategorySpending {
    private String category;
    private double totalAmount;
}
