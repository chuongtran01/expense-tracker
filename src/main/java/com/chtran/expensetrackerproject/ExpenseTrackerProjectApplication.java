package com.chtran.expensetrackerproject;

import com.chtran.expensetrackerproject.entity.Budget;
import com.chtran.expensetrackerproject.entity.Category;
import com.chtran.expensetrackerproject.entity.Expense;
import com.chtran.expensetrackerproject.entity.User;
import com.chtran.expensetrackerproject.repository.CategoryRepository;
import com.chtran.expensetrackerproject.repository.ExpenseRepository;
import com.chtran.expensetrackerproject.repository.UserRepository;
import com.chtran.expensetrackerproject.service.CategoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ExpenseTrackerProjectApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ExpenseRepository expenseRepository;

	@PostConstruct
	public void initUsers() {
		if (categoryRepository.count() == 0) {
			List<Category>categories = Stream.of(
					new Category(1, "Housing"),
					new Category(2, "Transportation"),
					new Category(3, "Food"),
					new Category(4, "Entertainment")
			).collect(Collectors.toList());
			categoryRepository.saveAll(categories);
		}

		if (userRepository.count() == 0) {
			List<User>users;

			User user = new User();
			user.setName("John Doe");
			user.setUsername("johndoe");
			user.setPassword("password");
			user.setRole("user");
			user.setOverallBudget(Double.MAX_VALUE);

			Budget categoryBudget1 = new Budget();
			categoryBudget1.setUser(user);
			categoryBudget1.setCategory(categoryService.getCategoryById(1));
			categoryBudget1.setAmount(Double.MAX_VALUE);

			Budget categoryBudget2 = new Budget();
			categoryBudget2.setUser(user);
			categoryBudget2.setCategory(categoryService.getCategoryById(2));
			categoryBudget2.setAmount(Double.MAX_VALUE);

			Budget categoryBudget3 = new Budget();
			categoryBudget3.setUser(user);
			categoryBudget3.setCategory(categoryService.getCategoryById(3));
			categoryBudget3.setAmount(Double.MAX_VALUE);

			Budget categoryBudget4 = new Budget();
			categoryBudget4.setUser(user);
			categoryBudget4.setCategory(categoryService.getCategoryById(4));
			categoryBudget4.setAmount(Double.MAX_VALUE);

			user.setBudgets(Arrays.asList(categoryBudget1, categoryBudget2, categoryBudget3, categoryBudget4));

			User user1 = new User();
			user1.setName("User2");
			user1.setUsername("user2");
			user1.setPassword("password");
			user1.setRole("user");
			user1.setOverallBudget(Double.MAX_VALUE);

			Budget categoryBudget11 = new Budget();
			categoryBudget11.setUser(user1);
			categoryBudget11.setCategory(categoryService.getCategoryById(1));
			categoryBudget11.setAmount(Double.MAX_VALUE);

			Budget categoryBudget21 = new Budget();
			categoryBudget21.setUser(user1);
			categoryBudget21.setCategory(categoryService.getCategoryById(2));
			categoryBudget21.setAmount(Double.MAX_VALUE);

			Budget categoryBudget31 = new Budget();
			categoryBudget31.setUser(user1);
			categoryBudget31.setCategory(categoryService.getCategoryById(3));
			categoryBudget31.setAmount(Double.MAX_VALUE);

			Budget categoryBudget41 = new Budget();
			categoryBudget41.setUser(user1);
			categoryBudget41.setCategory(categoryService.getCategoryById(4));
			categoryBudget41.setAmount(Double.MAX_VALUE);

			user1.setBudgets(Arrays.asList(categoryBudget11, categoryBudget21, categoryBudget31, categoryBudget41));


			users = Stream.of(
					user1, user
			).collect(Collectors.toList());
			userRepository.saveAll(users);
		}

	}

	@PostConstruct
	public void initExpense() {
		if (expenseRepository.count() == 0) {
			User user = userRepository.findById(1).orElse(null);
			Category category = categoryRepository.findById(1).orElse(null);
			Date date = new Date(2023, 8, 10);

			List<Expense>expenses = Stream.of(
					new Expense(1, user, category, date, "No desc", 12.99),
					new Expense(2, user, category, date, "No desc", 11.99),
					new Expense(3, user, category, date, "No desc", 15.99),
					new Expense(4, user, category, date, "No desc", 16.99)
			).collect(Collectors.toList());


			expenseRepository.saveAll(expenses);
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerProjectApplication.class, args);
	}

}
