package com.example.financedashboardpro.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardData {
    private List<CategorySpending> categorySpending;
    // Add other fields like netWorth, recentTransactions etc.
}
