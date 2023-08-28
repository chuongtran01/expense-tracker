package com.chtran.expensetrackerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpenseDTO {
    private int userId;
    private String category;
    private String description;
    private double amount;
}
