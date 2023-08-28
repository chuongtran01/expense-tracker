package com.chtran.expensetrackerproject.service;

import com.chtran.expensetrackerproject.entity.Category;
import com.chtran.expensetrackerproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    public List<Category> saveCategories(List<Category>categories) {
        return repository.saveAll(categories);
    }

    public Category getCategoryById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public Category getCategoryByName(String name) {
        return repository.findByName(name);
    }

}
