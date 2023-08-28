package com.chtran.expensetrackerproject.repository;

import com.chtran.expensetrackerproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Category findByName(String name);
}
