package com.chtran.expensetrackerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallBudgetDTO {
    private int userId;
    private double amount;
}
