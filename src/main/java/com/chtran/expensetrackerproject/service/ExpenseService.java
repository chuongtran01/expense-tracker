package com.chtran.expensetrackerproject.service;

import com.chtran.expensetrackerproject.entity.*;
import com.chtran.expensetrackerproject.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private  CategoryService categoryService;

    public List<Expense> getExpenseByUserId (int id) {
        return repository.findByUserId(id);
    }

    public List<ExpenseSummaryDTO> getExpenseSummaryByUserId (int id) {
        List<Expense> expenses = this.getExpenseByUserId(id);

        List<ExpenseSummaryDTO> returnedExpenses = new ArrayList<>();

        for (int i = 0; i < expenses.size(); i++) {
            returnedExpenses.add(new ExpenseSummaryDTO(
                    expenses.get(i).getUser().getName(),
                    expenses.get(i).getCategory().getName(),
                    expenses.get(i).getDate(),
                    expenses.get(i).getDescription(), expenses.get(i).getAmount()));
        }
        return returnedExpenses;
    }

    public List<ExpenseSummaryDTO>getExpenseByUserIdAndCategory(int id, String category) {
        List<Expense> expenses = this.getExpenseByUserId(id);

        List<ExpenseSummaryDTO> returnedExpenses = new ArrayList<>();

        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getCategory().getName().equals(category)) {
                returnedExpenses.add(new ExpenseSummaryDTO(
                        expenses.get(i).getUser().getName(),
                        expenses.get(i).getCategory().getName(),
                        expenses.get(i).getDate(),
                        expenses.get(i).getDescription(), expenses.get(i).getAmount()));

            }
        }
        return returnedExpenses;
    }

    public  List<ExpenseSummaryDTO>getExpenseByUserIdAndMonth(int id, int month) {
        List<Expense>expenses = this.getExpenseByUserId(id);

        List<ExpenseSummaryDTO>returnedExpenses = new ArrayList<>();

        for (Expense expense : expenses) {
            LocalDate localDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int expenseMonth = localDate.getMonthValue();

            if (expenseMonth == month) {
                returnedExpenses.add(new ExpenseSummaryDTO(expense.getUser().getName(), expense.getCategory().getName(), expense.getDate(), expense.getDescription(), expense.getAmount()));
            }
        }

        return returnedExpenses;
    }

    public  List<ExpenseSummaryDTO>getExpenseByUserIdAndYear(int id, int year) {
        List<Expense>expenses = this.getExpenseByUserId(id);

        List<ExpenseSummaryDTO>returnedExpenses = new ArrayList<>();

        for (Expense expense : expenses) {
            LocalDate localDate = expense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int expenseMonth = localDate.getYear();

            if (expenseMonth == year) {
                returnedExpenses.add(new ExpenseSummaryDTO(expense.getUser().getName(), expense.getCategory().getName(), expense.getDate(), expense.getDescription(), expense.getAmount()));
            }
        }

        return returnedExpenses;
    }

    public Expense createExpense(CreateExpenseDTO body) {
        User user = userService.getUserById(body.getUserId());
        Category category = categoryService.getCategoryByName(body.getCategory());

        if (user != null && category != null) {
            Expense expense = new Expense();
            expense.setUser(user);
            expense.setCategory(category);
            expense.setDate(new Date());
            expense.setAmount(body.getAmount());
            expense.setDescription(body.getDescription());

            return repository.save(expense);
        }

        return null;
    }

    public double getMoneySpentInMonth(int id, int month) {
        List<ExpenseSummaryDTO>expenses = this.getExpenseByUserIdAndMonth(id, month);

        double total = 0;

        for (ExpenseSummaryDTO expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public double getMoneySpentInYear(int id, int year) {
        List<ExpenseSummaryDTO>expenses = this.getExpenseByUserIdAndYear(id, year);

        double total = 0;

        for (ExpenseSummaryDTO expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public double getMoneySpentInMonthByCategory(int id, int month, String category) {
        List<ExpenseSummaryDTO>expenses = this.getExpenseByUserIdAndMonth(id, month);

        double total = 0;

        for (ExpenseSummaryDTO expense : expenses) {
            if (expense.getCategory().equals(category)) {
                total += expense.getAmount();
            }
        }

        return total;
    }

    public String getExpenseNotification(int id, int month) {
        double totalSpent = this.getMoneySpentInMonth(id, month);

        double BudgetMinusSpent = userService.getUserBudget(id) - totalSpent;

        if (BudgetMinusSpent < 0) {
            return "Exceeded limit, stop spending plz";
        }
        else if (BudgetMinusSpent >= 0 && BudgetMinusSpent <= 100) {
            return "Approaching limit";
        }
        else {
            return "Everything looks great, bro";
        }
    }

    public List<String>getExceededBudgetCategory(int id, int month) {
        List<String>result = new ArrayList<>();
        List<Category>categories = categoryService.getCategories();
        User exisitingUser = userService.getUserById(id);

        if (exisitingUser == null) {
            return null;
        }

        for (Category category : categories) {
            double budget = userService.getBudgetByCategoryName(id, category.getName());

            if (budget == Double.MAX_VALUE) {
                continue;
            }

            double moneySpentByCategory = this.getMoneySpentInMonthByCategory(id, month, category.getName());

            double BudgetMinusSpent = budget - moneySpentByCategory;

            if (BudgetMinusSpent < 0) {
                result.add("Exceeded limit, stop spending plz");
            }
            else if (BudgetMinusSpent >= 0 && BudgetMinusSpent <= 100) {
                result.add("Approaching limit");
            }
            else {
                result.add("Everything looks great, bro");
            }
        }

        return result;
    }
}
