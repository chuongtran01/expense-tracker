package com.chtran.expensetrackerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetDTO {
    private int userId;
    private String category;
    private double amount;
}
