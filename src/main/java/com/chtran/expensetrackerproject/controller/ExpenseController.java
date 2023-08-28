package com.chtran.expensetrackerproject.controller;

import com.chtran.expensetrackerproject.entity.*;
import com.chtran.expensetrackerproject.service.CategoryService;
import com.chtran.expensetrackerproject.service.ExpenseService;
import com.chtran.expensetrackerproject.service.UserService;
import org.hibernate.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseSummaryDTO>> getExpenseByUserId(@RequestParam int userId) {
        List<ExpenseSummaryDTO> expenses = expenseService.getExpenseSummaryByUserId(userId);

        if (expenses == null || expenses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expenses-by-category")
    public ResponseEntity<List<ExpenseSummaryDTO>> getExpenseByUserIdAndCategory(@RequestParam int userId, @RequestParam String category) {
        List<ExpenseSummaryDTO> expenses = expenseService.getExpenseByUserIdAndCategory(userId, category);

        if (expenses == null || expenses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expense-by-month")
    public ResponseEntity<List<ExpenseSummaryDTO>>getExpenseByUserIdAndMonth(@RequestParam int userId, @RequestParam int month) {
        List<ExpenseSummaryDTO> expenses = expenseService.getExpenseByUserIdAndMonth(userId, month);

        if (expenses == null || expenses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expense-by-year")
    public ResponseEntity<List<ExpenseSummaryDTO>>getExpenseByUserIdAndYear(@RequestParam int userId, @RequestParam int year) {
        List<ExpenseSummaryDTO> expenses = expenseService.getExpenseByUserIdAndYear(userId, year);

        if (expenses == null || expenses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense>createExpense(@RequestBody CreateExpenseDTO body) {
        if (body == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Expense newExpense = expenseService.createExpense(body);

        if (newExpense == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newExpense, HttpStatus.OK);
    }

    @GetMapping("/expenseNotification")
    public ResponseEntity<String>getExpenseNotification(@RequestParam int userId, @RequestParam int month) {
        String res = expenseService.getExpenseNotification(userId, month);

        if (res == null) {
            return new ResponseEntity<>("Cannot find user.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/expenseNotificationByCategory")
    public ResponseEntity<List<String>>getExpenseNotificationByCategory(@RequestParam int userId, @RequestParam int month) {
        List<String>res = expenseService.getExceededBudgetCategory(userId, month);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

