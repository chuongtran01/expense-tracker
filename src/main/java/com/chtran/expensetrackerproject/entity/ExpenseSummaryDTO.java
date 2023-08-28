package com.chtran.expensetrackerproject.entity;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSummaryDTO {
    private String name;
    private String category;
    private Date date;
    private String description;
    private double amount;
}
