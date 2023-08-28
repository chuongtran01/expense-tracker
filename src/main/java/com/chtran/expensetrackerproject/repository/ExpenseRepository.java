package com.chtran.expensetrackerproject.repository;

import com.chtran.expensetrackerproject.entity.Expense;
import com.chtran.expensetrackerproject.entity.ExpenseSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query(
            value = "SELECT * FROM EXPENSE e WHERE e.user_id = :id",
            nativeQuery = true
    )
    List<Expense> findByUserId(int id);

}
