package com.chtran.expensetrackerproject.service;

import com.chtran.expensetrackerproject.entity.Budget;
import com.chtran.expensetrackerproject.entity.User;
import com.chtran.expensetrackerproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CategoryService categoryService;


    @Transactional
    public User saveUser(User user) {
        User check = repository.findByUsername(user.getUsername()).orElse(null);

        if (check == null) {
            User newUser = new User();

            newUser.setName(user.getName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setRole(user.getRole());
            newUser.setOverallBudget(Double.MAX_VALUE);

            return repository.save(newUser);
        }

        return null;
    }

    @Transactional
    public List<User> saveUsers(List<User>users) {
        return repository.saveAll((users));
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User getUserByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    @Transactional
    public String deleteUser(int id) {
        repository.deleteById(id);
        return "User removed.";
    }

    @Transactional
    public String updateUser(User user) {
        User existingUser = repository.findById(user.getId()).orElse(null);


        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setRole(user.getRole());
            repository.save(existingUser);

            return "Successfully updated.";
        }

        return "Cannot find user.";
    }

    public double getUserBudget(int id) {
        User existingUser = repository.findById(id).orElse(null);
        return existingUser.getOverallBudget();
    }

    public String setOverallBudget(int id, double newBudget) {
        User existingUser = repository.findById(id).orElse(null);

        if (existingUser == null) {
            return "Cannot find user.";
        }

        existingUser.setOverallBudget(newBudget);
        repository.save(existingUser);
        return "Successfully updated.";
    }

    public double getBudgetByCategoryName(int id, String name) {
        User existingUser = repository.findById(id).orElse(null);

        for (Budget budget : existingUser.getBudgets()) {
            if (budget.getCategory().getName().equals(name)) {
                return budget.getAmount();
            }
        }

        return Double.MAX_VALUE;
    }

    public String setBudgetByCategory(int id, String name, double amount) {
        User existingUser = repository.findById(id).orElse(null);

        for (Budget budget : existingUser.getBudgets()) {
            if (budget.getCategory().getName().equals(name)) {
                budget.setAmount(amount);
                repository.save(existingUser);
                return "Successfully updated the budget.";
            }
        }

        Budget newBudget = new Budget();
        newBudget.setUser(existingUser);
        newBudget.setCategory(categoryService.getCategoryByName(name));
        newBudget.setAmount(amount);

        existingUser.getBudgets().add(newBudget);

        repository.save(existingUser);

        return "Successfully updated the budget.";
    }
}
